package com.ertugrul.spring.service;

import com.ertugrul.spring.converter.DebtMapper;
import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.dto.OverdueDebtDto;
import com.ertugrul.spring.dto.OverdueTotalDebtDto;
import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.enums.DebtType;
import com.ertugrul.spring.exception.DebtNotFoundException;
import com.ertugrul.spring.exception.DebtTypeException;
import com.ertugrul.spring.exception.UserNotFoundException;
import com.ertugrul.spring.service.entityservice.DebtEntityService;
import com.ertugrul.spring.service.entityservice.UserEntityService;
import com.ertugrul.spring.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DebtService {

    private final DebtEntityService debtEntityService;
    private final UserEntityService userEntityService;

    public List<DebtDto> findAll() {

        List<Debt> debtList = debtEntityService.findAll();

        List<DebtDto> debtDtoList = DebtMapper.INSTANCE.convertAllDebtToDebtDto(debtList);

        return debtDtoList;
    }

    public DebtDto findById(Long id) {

        Optional<Debt> optionalDebt = debtEntityService.findById(id);

        Debt debt;
        if (optionalDebt.isPresent()) {
            debt = optionalDebt.get();
        } else {
            throw new DebtNotFoundException("Debt not found!");
        }

        return DebtMapper.INSTANCE.convertDebtDtoToDebt(debt);
    }

    @Transactional
    public void delete(Long id) {

        Debt debt = findDebtById(id);

        debtEntityService.delete(debt);
    }

    private Debt findDebtById(Long id) {
        Optional<Debt> optionalDebt = debtEntityService.findById(id);

        Debt debt;
        if (optionalDebt.isPresent()) {
            debt = optionalDebt.get();
        } else {
            throw new DebtNotFoundException("Debt not found!");
        }
        return debt;
    }

    //a. Borç kaydeden, (sadece normal borçlar)
    @Transactional
    public DebtDto save(DebtDto debtDto) {
        Debt debt = DebtMapper.INSTANCE.convertDebtDtoToDebt(debtDto);
        DebtDto savedDto;

        Optional<User> user = userEntityService.findById(debtDto.getId());

        if (user.isPresent()) {
            if(debt.getType() == DebtType.LATE_FEE)
                throw new DebtTypeException("Late Fee type cannot be saved.");
            Debt savedDebt = debtEntityService.save(debt);
            savedDto = DebtMapper.INSTANCE.convertDebtDtoToDebt(savedDebt);
        } else
            throw new UserNotFoundException("User Id not found");
        return savedDto;
    }

    //d. Belirtilen tarihler arasında oluşturulan borçlar listelenebilmelidir.
    public List<DebtDto> listDebtsByDateRange(Date startDate, Date endDate) {
        Optional<List<Debt>> debtListOptional = debtEntityService.findAllDebtByExpiryDateBetween(startDate, endDate);

        if (debtListOptional.isPresent())
            return DebtMapper.INSTANCE.convertAllDebtToDebtDto(debtListOptional.get());
        else
            throw new DebtNotFoundException("No Debt has been found.");
    }

    //e. Bir kullanıcının tüm borçları listenelebilmelidir. (Borç tutarı sıfırdan büyük olanlar)
    public List<DebtDto> listAllUserDebtByUserId(Long userId) {
        List<DebtDto> debtList;
        Optional<User> user = userEntityService.findById(userId);
        if (user.isPresent()) {
            Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllDebtByUserId(userId);
            if (allDebtByUserId.isPresent())
                debtList = DebtMapper.INSTANCE.convertAllDebtToDebtDto(allDebtByUserId.get());
            else
                throw new DebtNotFoundException("Debt not found");
        } else
            throw new UserNotFoundException("User Id not found");

        return debtList;
    }

    //f. Bir kullanıcının vadesi geçmiş borçları listenelebilmelidir. (Borç tutarı sıfırdan büyük olanlar)
    public List<DebtDto> listAllUserOverdueDebtByUserId(Long userId) {
        List<DebtDto> debtList;
        Optional<User> user = userEntityService.findById(userId);
        if (user.isPresent()) {
            Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllOverdueDebtByUserId(userId);
            if (allDebtByUserId.isPresent())
                debtList = DebtMapper.INSTANCE.convertAllDebtToDebtDto(allDebtByUserId.get());
            else
                throw new DebtNotFoundException("Debt not found");
        } else
            throw new UserNotFoundException("User Id not found");
        return debtList;
    }

    //h. Bir kullanıcının vadesi geçmiş toplam borç tutarını dönen bir servis olmaldır.
    public OverdueTotalDebtDto findTotalOverdueDebtByUserId(Long userId) {
        List<DebtDto> debtDtoList = listAllUserOverdueDebtByUserId(userId);
        OverdueTotalDebtDto overdueTotalDebtDto = null;
        if (debtDtoList != null) {
            double totalDebt = debtDtoList.stream().mapToDouble(DebtDto::getTotalAmount).sum();
            overdueTotalDebtDto = new OverdueTotalDebtDto(userId, totalDebt);
        }
        return overdueTotalDebtDto;
    }

    // i. Bir kullanıcının anlık gecikme zammı tutarını dönen bir servis olmalıdır.
    // (Vadesi geçen borçlara hesaplanan gecikme zamı tutarları toplamı. (Sadece gecikme zammı))
    public OverdueDebtDto findAllOverdueDebtLateFeeAmountByUserId(Long userId) {
        double overDueDebtDto = 0.0;
        List<DebtDto> debtDtoList = listAllUserOverdueDebtByUserId(userId);
        for (DebtDto dto : debtDtoList) {
            long days = TimeUnit.DAYS.convert(new Date().getTime() - dto.getExpiryDate().getTime(), TimeUnit.MILLISECONDS);
            overDueDebtDto += dto.getTotalAmount() * days * Constant.getLateFeeRate(dto.getExpiryDate()) / 100;
        }
        return new OverdueDebtDto(userId, overDueDebtDto);
    }

    //4 - B. Belirtilen tarihler arasında yapılan tahsilatlar listelenebilmelidir
    public List<DebtDto> findAllLateFeeDebtByUserId(Long userId) {
        List<DebtDto> debtList;
        Optional<User> user = userEntityService.findById(userId);
        if (user.isPresent()) {
            Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllDebtByUserIdAndType(userId);
            if (allDebtByUserId.isPresent())
                debtList = DebtMapper.INSTANCE.convertAllDebtToDebtDto(allDebtByUserId.get());
            else
                throw new DebtNotFoundException("Debt not found");
        } else
            throw new UserNotFoundException("User Id not found");
        return debtList;
    }
}
