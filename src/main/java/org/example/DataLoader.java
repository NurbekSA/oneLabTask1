package org.example;

import org.example.model.*;
import org.example.repository.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

// Так как это просто для практики все добавляется через Репозиторий


@Component
public class DataLoader {
    private BusinessRepo businessRepo;
    private CardRepo cardRepo;
    private InvestmentRepo investmentRepo;
    private InvestorRepo investorRepo;
    private OrderRepo orderRepo;

    public DataLoader(BusinessRepo businessRepo, CardRepo cardRepo, InvestmentRepo investmentRepo, InvestorRepo investorRepo, OrderRepo orderRepo) {
        this.businessRepo = businessRepo;
        this.cardRepo = cardRepo;
        this.investmentRepo = investmentRepo;
        this.investorRepo = investorRepo;
        this.orderRepo = orderRepo;
    }


    public void initDatabase() {
        long now = System.currentTimeMillis();


        System.out.println("Data Loader working ---1");
        // Инициализация бизнес-модели
        BusinessModel business = new BusinessModel(null, null, "Example Corp", "123456789",
                "123 Business St", "Credit Card",
                "Technology", now,
                "John Doe", "123456789012",
                "+77011234567", "contact@example.com",
                now, now);
        businessRepo.save(business);

        // Имитация регистрации инвесторов
        List<InvestorModel> investors = Arrays.asList(
                new InvestorModel(null, null, null, true,new BigDecimal("10.0"),
                        "123456789", "John Doe", "+77011234567",
                        "Nurbek@example.com", "Address 1", "individual", now, now),
                new InvestorModel(null, null, null, true,new BigDecimal("15.0"),
                        "987654321", "Jane Smith", "+77019876543",
                        "Nurdaulet@example.com", "Address 2", "corporate", now, now)
        );
        investorRepo.saveAll(investors);

        // Имитация регистрации карт
        List<CardModel> cardModels = Arrays.asList(
                new CardModel(null, investors.get(0), "1234 5678 9012 3456", "John Doe", "12/25", now, now),
                new CardModel(null, investors.get(1), "9876 5432 1098 7654", "Jane Smith", "11/26", now, now)
        );
        cardRepo.saveAll(cardModels);

        // Имитация создания заказа
        List<OrderModel> orders = Arrays.asList(
                new OrderModel(null, null, business, true, true,
                        "EQUITY", new BigDecimal("10000.00"),
                        new BigDecimal("5000.00"), "USD", now,
                        now + 604800000L, // dueDate one week from now
                        "PENDING", "Business Expansion",
                        "Expansion of operations to new regions",
                        "Real Estate", "null")
        );

        orderRepo.saveAll(orders);

        //Имитация cоздание инвестиций
        List<InvestmentModel> investments = Arrays.asList(
                new InvestmentModel(null,investors.get(0), orders.get(0), true, false, true,
                        new BigDecimal("1000.00"), now)
        );
        investmentRepo.saveAll(investments);
    }
}
