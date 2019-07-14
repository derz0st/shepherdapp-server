package com.paazl.service;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShepherdServiceTest {
//    @Mock
//    private SheepRepository sheepRepository;
//    @Mock
//    private CurrentBalanceRepository currentBalanceRepository;
//    private ShepherdService subject;
//
//    @Before
//    public void setUp() throws Exception {
//        subject = new ShepherdService(sheepRepository, currentBalanceRepository, 500);
//    }
//
//    @Test
//    public void orderNewSheep_whenBalanceIsNotEnough_returnException() {
//
//
//        BigInteger balance = BigInteger.valueOf(1000);
//        CurrentBalance currentBalance = new CurrentBalance(balance);
//        when(currentBalanceRepository.findFirstByOrderByTimestampDesc())
//                .thenReturn(currentBalance);
//
//        String actual = subject.orderSheeps(2);
//
//        verify(currentBalanceRepository).save(currentBalance);
//
//        assertThat(actual).isEqualTo("In total 2 sheep were ordered and added to your flock!");
//
//    }
}
