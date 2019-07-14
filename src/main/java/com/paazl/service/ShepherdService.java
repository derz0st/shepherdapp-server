package com.paazl.service;

import com.paazl.data.CurrentBalance;
import com.paazl.data.repositories.CurrentBalanceRepository;
import com.paazl.util.CurrentBalanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ShepherdService {
    private CurrentBalanceRepository currentBalanceRepository;

    @Autowired
    public ShepherdService(CurrentBalanceRepository currentBalanceRepository) {
		this.currentBalanceRepository = currentBalanceRepository;
	}

    public BigInteger getBalance() {
        return currentBalanceRepository.findFirstByOrderByTimestampDesc().getBalance();
    }

    public CurrentBalance withdrawBalance(BigInteger balanceToWithdraw) {
        CurrentBalance balance = currentBalanceRepository.findFirstByOrderByTimestampDesc();
        return currentBalanceRepository.save(CurrentBalanceUtils.subtractBalance(balance, balanceToWithdraw));
    }
}
