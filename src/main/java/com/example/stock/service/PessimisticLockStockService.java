package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* Pessimistic Lock 의 장점
* 충돌이 빈번하게 일어난다면 Optimistic Lock 보다 성능이 좋을 수 있다. 락을 통해 update 를 제어하기 때문에 데이터 정합성이 어느정도 보장된다.
* 별도의 락을 잡기 때문에 성능 감소가 있을 수 있다.
* */
@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
