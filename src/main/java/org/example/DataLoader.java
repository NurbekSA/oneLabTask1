package org.example;

import org.example.model.*;
import org.example.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(AdminRepo adminRepo, BusinessRepo businessRepo, CardRepo cardRepo, InvestmentRepo investmentRepo, InvestorRepo investorRepo, OrderRepo orderRepo) {
        System.out.println("ODLKN:LKASM:LDKALKSDJ:FLKSMD:FLKMSD:LKFM:SLDMF:LKSDMFLMSD:fLKMSD:lkfM:LSDNF:LDSFLKSDFJSDLKFJ:LSDJF:LSKDF:LKSDLF:K");
        long now = System.currentTimeMillis();

        return args -> {
            // Создание инвесторов
            List<InvestorModel> investors = Arrays.asList(
                    new InvestorModel(1l, "123456789", "John Doe", "+77011234567", "john@example.com", "Address 1", "individual", now, now),
                    new InvestorModel(2l, "987654321", "Jane Smith", "+77019876543", "jane@example.com", "Address 2", "corporate", now, now),
                    new InvestorModel(3l, "12345678910", "Alice Johnson", "+77017654321", "alice@example.com", "Address 3", "individual", now, now)
            );
            investorRepo.saveAll(investors);

            // Создание карт
            List<CardModel> cardModels = Arrays.asList(
                    new CardModel(1l, investors.get(0), "1234 5678 9012 3456", "John Doe", "12/25", now, now),
                    new CardModel(2l, investors.get(1), "2345 6789 0123 4567", "Jane Smith", "11/24", now, now),
                    new CardModel(3l, investors.get(2), "3456 7890 1234 5678", "Alice Johnson", "10/23", now, now)
            );
            cardRepo.saveAll(cardModels);

            // Создание заказов
            List<OrderModel> orders = Arrays.asList(
                    new OrderModel(null, investors.get(0), "EQUITY", new BigDecimal("10000.00"), new BigDecimal("5000.00"), "USD", now, now, "PENDING", "Business Expansion", "Expansion of operations to new regions", "Real Estate", null),
                    new OrderModel(null, investors.get(1), "DEBT", new BigDecimal("20000.00"), new BigDecimal("15000.00"), "EUR", now, now, "APPROVED", "Working Capital", "Funding for daily operations", "Equipment", null),
                    new OrderModel(null, investors.get(2), "EQUITY", new BigDecimal("5000.00"), new BigDecimal("2500.00"), "USD", now, now, "REJECTED", "Market Research", "Research and analysis for market trends", "Intellectual Property", null)
            );
            orderRepo.saveAll(orders);

            // Создание инвестиций
            List<InvestmentModel> investments = Arrays.asList(
                    new InvestmentModel(null, investors.get(0), orders.get(0), new BigDecimal("1000.00"), now, "PENDING"),
                    new InvestmentModel(null, investors.get(1), orders.get(1), new BigDecimal("2000.00"), now, "COMPLETED"),
                    new InvestmentModel(null, investors.get(2), orders.get(2), new BigDecimal("1500.00"), now, "PENDING")
            );
            investmentRepo.saveAll(investments);
        };
    }
}
