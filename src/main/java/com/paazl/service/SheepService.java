package com.paazl.service;

import com.paazl.data.Sheep;
import com.paazl.data.State;
import com.paazl.data.repositories.SheepRepository;
import com.paazl.exception.NotEnoughMoneyException;
import com.paazl.transformer.SheepTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class SheepService {
    private final Integer priceOfSheep;
    private final ShepherdService shepherdService;
    private final SheepRepository sheepRepository;
    private final SheepTransformer sheepTransformer;

    @Autowired
    public SheepService(ShepherdService shepherdService,
                        SheepRepository sheepRepository,
                        SheepTransformer sheepTransformer,
                        @Value("${price_of_new_sheep}") Integer priceOfSheep) {
        this.shepherdService = shepherdService;
        this.sheepRepository = sheepRepository;
        this.sheepTransformer = sheepTransformer;
        this.priceOfSheep = priceOfSheep;
    }

    public SheepStatusesDto getSheepStatuses() {
        List<Sheep> sheeps = sheepRepository.findAll();

        Map<State, List<Sheep>> sheepsByState = sheeps.stream()
                .collect(groupingBy(Sheep::getState, toList()));

        return new SheepStatusesDto(
                sheepsByState.getOrDefault(State.HEALTHY, Collections.emptyList()).size(),
                sheepsByState.getOrDefault(State.DEAD, Collections.emptyList()).size()
        );
    }

    @Transactional
    public List<SheepDto> orderSheeps(int numberOfSheepDesired) {
        BigInteger balance = shepherdService.getBalance();
        BigInteger totalPrice = BigInteger.valueOf(numberOfSheepDesired * priceOfSheep);

        if (balance.compareTo(totalPrice) < 0) {
            throw new NotEnoughMoneyException(balance, totalPrice);
        }

        shepherdService.withdrawBalance(totalPrice);

        List<Sheep> sheeps = createSheeps(numberOfSheepDesired);
        return sheepTransformer.toDto(sheeps);
    }

    private List<Sheep> createSheeps(int numberOfSheepDesired) {
        return IntStream.range(0, numberOfSheepDesired)
                .mapToObj(number -> sheepRepository.save(new Sheep()))
                .collect(toList());
    }
}
