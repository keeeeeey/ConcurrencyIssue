package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* Optimistic Lock 의 특징
* 별도의 락을 잡지 않으므로 Pessimistic Lock 보다 성능상 이점이 있지만 Update 가 실패했을때 재시도 로직을 개발자가 직접 작성을 해줘야 한다.
* 충돌이 빈번하게 일어난다면 Pessimistic Lock 을 사용하는 것이 더 낫다.
* */
@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
