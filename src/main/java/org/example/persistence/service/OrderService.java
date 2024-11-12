package org.example.persistence.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.OrderModelDTO;
import org.example.persistence.model.entity.OrderModel;
import org.example.persistence.model.exception.ResourceNotFoundException;
import org.example.persistence.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    // Преобразование из OrderModel в OrderModelDTO
    private OrderModelDTO convertToDto(OrderModel orderModel) {
        return new OrderModelDTO(
                orderModel.getId(),
                orderModel.getInvestments().stream().map(investment -> investment.getId()).collect(Collectors.toList()),
                orderModel.getBusiness() != null ? orderModel.getBusiness().getId() : null,
                orderModel.getIsActive(),
                orderModel.getIsAlive(),
                orderModel.getInvestmentType(),
                orderModel.getTargetAmount(),
                orderModel.getActualAmount(),
                orderModel.getCurrency(),
                orderModel.getPurpose(),
                orderModel.getDescription(),
                orderModel.getCollateral(),
                orderModel.getDateOfOrder(),
                orderModel.getDueDate()
        );
    }

    // Преобразование из OrderModelDTO в OrderModel
    private OrderModel convertToEntity(OrderModelDTO orderDTO) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderDTO.getId());
        orderModel.setIsActive(orderDTO.getIsActive());
        orderModel.setIsAlive(orderDTO.getIsAlive());
        orderModel.setInvestmentType(orderDTO.getInvestmentType());
        orderModel.setTargetAmount(orderDTO.getTargetAmount());
        orderModel.setActualAmount(orderDTO.getActualAmount());
        orderModel.setCurrency(orderDTO.getCurrency());
        orderModel.setPurpose(orderDTO.getPurpose());
        orderModel.setDescription(orderDTO.getDescription());
        orderModel.setCollateral(orderDTO.getCollateral());
        orderModel.setDateOfOrder(orderDTO.getDateOfOrder());
        orderModel.setDueDate(orderDTO.getDueDate());
        return orderModel;
    }

    public List<OrderModelDTO> findAll() {
        return orderRepo.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public OrderModelDTO findById(Long id) {
        OrderModel orderModel = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        return convertToDto(orderModel);
    }

    public OrderModelDTO create(OrderModelDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("Order DTO cannot be null");
        }
        OrderModel orderModel = convertToEntity(orderDTO);
        orderModel.setDateOfOrder(System.currentTimeMillis()); // Устанавливаем дату заказа
        OrderModel savedOrder = orderRepo.save(orderModel);
        return convertToDto(savedOrder);
    }

    public void delete(Long id) {
        if (!orderRepo.existsById(id)) {
            throw new ResourceNotFoundException("Order with id " + id + " not found");
        }
        orderRepo.deleteById(id);
    }
}
