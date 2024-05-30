package com.zouliga.order;

import com.zouliga.customer.CustomerClient;
import com.zouliga.excetion.BusinessException;
import com.zouliga.orderLine.OrderLineRequest;
import com.zouliga.orderLine.OrderLineService;
import com.zouliga.product.ProductClient;
import com.zouliga.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    public Integer createOrder(OrderRequest request) {
        // check the customer
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(
                        () -> new BusinessException("Can not create order:: No customer exists with the provided ID:: "+ request.customerId())
                );

        // purchase the products --> product microservice
        productClient.purchaseProducts(request.products());

        // persist order
        var order = orderRepository.save(mapper.toOrder(request));

        // persist order lines
        for(PurchaseRequest purchaseRequest: request.products()){
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
        return null;
    }
}
