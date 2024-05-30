package com.zouliga.order;

import com.zouliga.customer.CustomerClient;
import com.zouliga.excetion.BusinessException;
import com.zouliga.kafka.OrderConfirmation;
import com.zouliga.kafka.OrderProducers;
import com.zouliga.orderLine.OrderLineRequest;
import com.zouliga.orderLine.OrderLineService;
import com.zouliga.product.ProductClient;
import com.zouliga.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducers orderProducers;

    public Integer createOrder(OrderRequest request) {
        // check the customer
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(
                        () -> new BusinessException("Can not create order:: No customer exists with the provided ID:: " + request.customerId())
                );

        // purchase the products --> product microservice
        var purchaseProducts = productClient.purchaseProducts(request.products());

        // persist order
        var order = orderRepository.save(mapper.toOrder(request));

        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process

        // send the order confirmation --> modification microservice (kafka)
        orderProducers.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order find with provided ID: %s", orderId)));
    }
}
