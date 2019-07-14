package com.paazl.service;

import com.paazl.data.Sheep;
import com.paazl.data.State;
import com.paazl.data.repositories.SheepRepository;
import com.paazl.exception.NotEnoughMoneyException;
import com.paazl.transformer.SheepTransformer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SheepServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ShepherdService shepherdService;
    @Mock
    private SheepRepository sheepRepository;
    @Spy
    private SheepTransformer sheepTransformer;
    private SheepService subject;

    @Before
    public void setUp() {
        subject = new SheepService(shepherdService, sheepRepository, sheepTransformer, 500);
    }

    @Test
    public void getSheepStatuses_whenSheepsArePresent_returnNumberOfSheeps() {
        Sheep healthySheep = new Sheep();
        Sheep deadSheep = new Sheep();
        deadSheep.setState(State.DEAD);

        when(sheepRepository.findAll()).thenReturn(Arrays.asList(healthySheep, deadSheep));

        SheepStatusesDto actual = subject.getSheepStatuses();

        verify(sheepRepository).findAll();

        assertThat(actual).isNotNull();
        assertThat(actual.getNumberOfHealthySheep()).isEqualTo(1);
        assertThat(actual.getNumberOfDeadSheep()).isEqualTo(1);
    }

    @Test
    public void getSheepStatuses_whenSheepsAreNotPresent_returnDtoWithZeroSheeps() {
        when(sheepRepository.findAll()).thenReturn(Collections.emptyList());

        SheepStatusesDto actual = subject.getSheepStatuses();

        verify(sheepRepository).findAll();

        assertThat(actual).isNotNull();
        assertThat(actual.getNumberOfHealthySheep()).isEqualTo(0);
        assertThat(actual.getNumberOfDeadSheep()).isEqualTo(0);
    }

    @Test
    public void orderSheeps_whenEnoughMoney_createSheepsAndReturnDtoList() {
        when(shepherdService.getBalance()).thenReturn(BigInteger.valueOf(2000));
        when(sheepRepository.save(any(Sheep.class))).then(returnsFirstArg());

        List<SheepDto> actual = subject.orderSheeps(2);

        verify(shepherdService).getBalance();
        verify(sheepRepository, times(2)).save(any(Sheep.class));
        verify(shepherdService).withdrawBalance(BigInteger.valueOf(1000));
        verify(sheepTransformer).toDto(anyList());

        assertThat(actual).hasSize(2);
    }

    @Test
    public void orderSheeps_whenNotEnoughMoney_throwsNotEnoughMoneyException() {
        when(shepherdService.getBalance()).thenReturn(BigInteger.valueOf(800));

        expectedException.expect(NotEnoughMoneyException.class);
        expectedException.expectMessage("There is not enough money on your balance " +
                "to make order. Balance: 800, total price: 1000");

        List<SheepDto> actual = subject.orderSheeps(2);
    }
}
