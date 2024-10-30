package com.example.webstore.service;

import com.example.webstore.dto.order.CreateOrderRequestDto;
import com.example.webstore.dto.order.OrderDto;
import com.example.webstore.dto.order.UpdateOrderRequestDto;
import com.example.webstore.dto.order.item.OrderItemDto;
import com.example.webstore.mapper.OrderItemMapper;
import com.example.webstore.mapper.OrderMapper;
import com.example.webstore.model.CartItem;
import com.example.webstore.model.Order;
import com.example.webstore.model.OrderItem;
import com.example.webstore.model.ShoppingCart;
import com.example.webstore.model.User;
import com.example.webstore.repository.OrderItemRepository;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.repository.ShoppingCartRepository;
import com.example.webstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public OrderDto placeOrder(Long userId, Authentication authentication, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Your shopping cart is empty"));
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Your shopping cart is empty");
        }
        Order newOrder = initNewOrder(requestDto, userId);
        BigDecimal total = BigDecimal.ZERO;
        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> getOrderItem(cartItem, newOrder, total))
                .collect(Collectors.toSet());
        newOrder.setOrderItems(orderItems);
        orderRepository.save(newOrder);

        shoppingCartRepository.deleteById(userId);
        if (requestDto.isSaveUserInfo()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Couldn't find and update user by id " + userId));
            user.setPhone(requestDto.getPhone());
            user.setAddress(requestDto.getShippingAddress());
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    user,
                    authentication.getCredentials(),
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        return orderMapper.toDto(newOrder);
    }

    public List<OrderDto> getOrders(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderItemDto> getOrderItemsByOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id "
                        + orderId));
        return order.getOrderItems().stream().map(orderItemMapper::toDto).toList();
    }

    public OrderItemDto getItemById(Long userId, Long orderId, Long itemId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id "
                        + orderId));
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, order.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find item by id " + itemId));
        return orderItemMapper.toDto(orderItem);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id "
                        + orderId));
        order.setStatus(requestDto.status());
        return orderMapper.toDto(order);
    }

    private Order initNewOrder(CreateOrderRequestDto requestDto, Long userId) {
        Order order = new Order();
        order.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id" + userId)));
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setPhone(requestDto.getPhone());
        order.setTotal(BigDecimal.ZERO);
        return order;
    }

    private OrderItem getOrderItem(CartItem cartItem, Order order, BigDecimal total) {
        OrderItem orderItem = orderItemMapper.toOrderItem(cartItem);
        orderItem.setPrice(
                orderItem.getPrice()
                        .multiply(new BigDecimal(orderItem.getQuantity()))
        );
        order.setTotal(total.add(orderItem.getPrice()).add(order.getTotal()));
        orderItem.setOrder(order);
        return orderItem;
    }
}
