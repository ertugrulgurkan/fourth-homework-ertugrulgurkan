package com.ertugrul.spring.service;

import com.ertugrul.spring.converter.DebtMapper;
import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.dto.OverdueDebtDto;
import com.ertugrul.spring.dto.OverdueTotalDebtDto;
import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.enums.DebtType;
import com.ertugrul.spring.exception.DebtTypeException;
import com.ertugrul.spring.service.entityservice.DebtEntityService;
import com.ertugrul.spring.service.entityservice.UserEntityService;
import com.ertugrul.spring.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebtService {

    private final DebtEntityService debtEntityService;
    private final UserEntityService userEntityService;
    private final ValidationService validationService;

    public List<DebtDto> findAll() {

        List<Debt> debtList = debtEntityService.findAll();

        return DebtMapper.INSTANCE.convertAllDebtToDebtDto(debtList);
    }

    public DebtDto findById(Long id) {

        Optional<Debt> optionalDebt = debtEntityService.findById(id);

        Debt debt = validationService.validateDebt(optionalDebt);

        return DebtMapper.INSTANCE.convertDebtDtoToDebt(debt);
    }

    @Transactional
    public void delete(Long id) {

        Debt debt = findDebtById(id);

        debtEntityService.delete(debt);
    }

    private Debt findDebtById(Long id) {
        Optional<Debt> optionalDebt = debtEntityService.findById(id);

        return validationService.validateDebt(optionalDebt);
    }

    //a. Borç kaydeden, (sadece normal borçlar)
    @Transactional
    public DebtDto save(DebtDto debtDto) {
        Debt debt = DebtMapper.INSTANCE.convertDebtDtoToDebt(debtDto);
        DebtDto savedDto;

        Optional<User> optionalUser = userEntityService.findById(debtDto.getUserId());

        validationService.validateUser(optionalUser);

        if (debt.getType() == DebtType.LATE_FEE)
            throw new DebtTypeException("Late Fee type cannot be saved.");

        debt.setRemainingAmount(debt.getTotalAmount()); //kalan tutar ilk seferde ana borc tutarına eşittir
        Debt savedDebt = debtEntityService.save(debt);
        savedDto = DebtMapper.INSTANCE.convertDebtDtoToDebt(savedDebt);

        return savedDto;
    }

    //d. Belirtilen tarihler arasında oluşturulan borçlar listelenebilmelidir.
    public List<DebtDto> listDebtsByDateRange(Date startDate, Date endDate) {
        Optional<List<Debt>> debtListOptional = debtEntityService.findAllDebtByExpiryDateBetween(startDate, endDate);

        List<Debt> debts = validationService.validateDebtList(debtListOptional);

        return DebtMapper.INSTANCE.convertAllDebtToDebtDto(debts);
    }

    //e. Bir kullanıcının tüm borçları listenelebilmelidir. (Borç tutarı sıfırdan büyük olanlar)
    public List<DebtDto> listAllUserDebtByUserId(Long userId) {

        Optional<User> user = userEntityService.findById(userId);

        validationService.validateUser(user);

        Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllDebtByUserId(userId);

        List<Debt> debts = validationService.validateDebtList(allDebtByUserId);

        return DebtMapper.INSTANCE.convertAllDebtToDebtDto(debts);
    }

    //f. Bir kullanıcının vadesi geçmiş borçları listenelebilmelidir. (Borç tutarı sıfırdan büyük olanlar)
    public List<DebtDto> listAllUserOverdueDebtByUserId(Long userId) {
        Optional<User> userOptional = userEntityService.findById(userId);

        validationService.validateUser(userOptional);

        Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllOverdueDebtByUserId(userId);

        List<Debt> debts = validationService.validateDebtList(allDebtByUserId);

        return DebtMapper.INSTANCE.convertAllDebtToDebtDto(debts);
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
            overDueDebtDto += Constant.calculateLateFee(dto.getTotalAmount(),dto.getExpiryDate());;
        }
        return new OverdueDebtDto(userId, overDueDebtDto);
    }

    //4 - d. Kullanıcının ödediği toplam gecikme zammı listelenebilmelidir
    public List<DebtDto> findAllLateFeeDebtByUserId(Long userId) {
        Optional<User> user = userEntityService.findById(userId);

        validationService.validateUser(user);

        Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllDebtByUserIdAndType(userId);

        List<Debt> debts = validationService.validateDebtList(allDebtByUserId);

        return DebtMapper.INSTANCE.convertAllDebtToDebtDto(debts);
    }
}
