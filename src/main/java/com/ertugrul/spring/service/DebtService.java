package com.ertugrul.spring.service;

import com.ertugrul.spring.converter.DebtMapper;
import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.exception.DebtNotFoundException;
import com.ertugrul.spring.exception.UserNotFoundException;
import com.ertugrul.spring.service.entityservice.DebtEntityService;
import com.ertugrul.spring.service.entityservice.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebtService {
    private final DebtEntityService debtEntityService;
    private final UserEntityService userEntityService;

    //e. Bir kullanıcının tüm borçları listenelebilmelidir. (Borç tutarı sıfırdan büyük olanlar)
    public List<DebtDto> listAllUserDebt(Long userId){
        List<DebtDto> debtList;
        Optional<User> user = userEntityService.findById(userId);
        if (user.isPresent()){
            Optional<List<Debt>> allDebtByUserId = debtEntityService.findAllDebtByUserId(userId);
            if (allDebtByUserId.isPresent())
                debtList = DebtMapper.INSTANCE.convertAllDebtDtoToDebt(allDebtByUserId.get());
            else
                throw new DebtNotFoundException("Debt not found");
        }
        else
            throw new UserNotFoundException("User Id not found");

        return debtList;
    }

    //a. Borç kaydeden, (sadece normal borçlar)
    public DebtDto save(DebtDto debtDto) {
        Debt debt = DebtMapper.INSTANCE.convertDebtDtoToDebt(debtDto);
        DebtDto savedDto;

        Optional<User> user = userEntityService.findById(debtDto.getId());

        if (user.isPresent()){
            Debt savedDebt = debtEntityService.save(debt);
            savedDto = DebtMapper.INSTANCE.convertDebtDtoToDebt(savedDebt);
        }
        else
            throw new UserNotFoundException("User Id not found");
        return savedDto;
    }

    //d. Belirtilen tarihler arasında oluşturulan borçlar listelenebilmelidir.
    public List<DebtDto> listDebtsByDateRange(Date startDate, Date endDate){
        Optional<List<Debt>> debtListOptional = debtEntityService.findAllDebtByExpiryDateBetween(startDate, endDate);

        if (debtListOptional.isPresent())
            return DebtMapper.INSTANCE.convertAllDebtDtoToDebt(debtListOptional.get());
        else
            throw new DebtNotFoundException("No Debt has been found.");
    }
}
