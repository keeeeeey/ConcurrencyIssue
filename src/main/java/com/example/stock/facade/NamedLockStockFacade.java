package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
* NamedLock 특징
* NamedLock 은 주로 분산락을 구현할 때 사용한다.
* Pessimistic Lock은 Timeout을 구현하기 힘들지만 NamedLock은 손쉽게 구현할 수 있다. 데이터 삽입시에 정합성을 맞춰야하는 경우에도 자주 사용된다.
* 트랜잭션 해제시에 Lock 해제와 Session 관리를 잘 해줘야 하므로 주의해서 사용해야 하고 구현 방법이 복잡해질 수 있다.
* */
@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockService stockService;

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            stockService.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
