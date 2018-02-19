package me.zj22.gudao.server.web.service.impl;

import me.zj22.gudao.server.web.dao.db.OrderDetailMapper;
import me.zj22.gudao.server.web.dao.db.OrderMapper;
import me.zj22.gudao.server.web.dao.db.UserMapper;
import me.zj22.gudao.server.web.enums.OrderStatusEnum;
import me.zj22.gudao.server.web.pojo.dto.Order;
import me.zj22.gudao.server.web.pojo.dto.OrderDetail;
import me.zj22.gudao.server.web.pojo.vo.OrderDTO;
import me.zj22.gudao.server.web.pojo.vo.Page;
import me.zj22.gudao.server.web.service.OrderService;
import me.zj22.gudao.server.web.utils.IdWorkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单Service
 * @Program:zj22-gudao-server
 * @Description:
 * @Author Gqjian
 * @Create 2018/2/13 19:50:57
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = new Order();
        //获得一个订单ID
        IdWorkerUtil idWork = new IdWorkerUtil(0,0);
        Long orderId = idWork.nextId();
        //补全pojo
        orderDTO.setOrderId(orderId.toString());
        //订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        Date time = new Date();
        long date = time.getTime();
        orderDTO.setCreateTime(date);
        //插入订单表
        order.setOrderId(orderDTO.getOrderId());
        order.setOrderStatus(OrderStatusEnum.NEW.getCode());
        order.setCreateTime(date);
        orderMapper.insert(order);
        //插入订单表明细
        List<OrderDetail> orderDetails = orderDTO.getOrderDetailList();
        for (OrderDetail orderDetail:orderDetails) {
            Long orderDetailId = idWork.nextId();
            orderDetail.setDetailId(orderDetailId.toString());
            orderDetail.setOrderId(orderId.toString());
            orderDetailMapper.insert(orderDetail);
        }
        return orderDTO;
    }

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId.toString());
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId.toString());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setReceiverName(order.getReceiverName());
        orderDTO.setReceiverPhone(order.getReceiverPhone());
        orderDTO.setProv(order.getProv());
        orderDTO.setCity(order.getCity());
        orderDTO.setCounty(order.getCounty());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setZipcode(order.getZipcode());
        orderDTO.setOrderAmount(order.getOrderAmount());
        orderDTO.setPostage(order.getPostage());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setCreateTime(order.getCreateTime());
        orderDTO.setOperationTime(order.getOperationTime());
        orderDTO.setOperator(order.getOperator());
        orderDTO.setRemark(order.getRemark());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAllListOrder(Page<OrderDTO> page, String openId) {

        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
