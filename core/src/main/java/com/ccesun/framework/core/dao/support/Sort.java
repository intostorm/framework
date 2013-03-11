package com.ccesun.framework.core.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sort implements
		Iterable<Sort.OrderEntry>, Serializable {

	private static final long serialVersionUID = -5777518191013089564L;

	public static final String DEFAULT_ORDER = OrderEntry.ORDER_ASC;

	private List<OrderEntry> orders = new ArrayList<OrderEntry>();
	
	public Sort asc(String orderBy) {
        this.orders.add(new OrderEntry(orderBy, OrderEntry.ORDER_ASC));
        return this;
    }
    
    public Sort desc(String orderBy) {
        this.orders.add(new OrderEntry(orderBy, OrderEntry.ORDER_DESC));
        return this;
    }
    
    public Sort order(String orderBy, String order) {
        this.orders.add(new OrderEntry(orderBy, order));
        return this;
    }
	
	public static class OrderEntry {

		public static final String ORDER_ASC = "ASC";
		public static final String ORDER_DESC = "DESC";
		
		private String orderBy;
		private String order;
		
		public OrderEntry(String orderBy, String order) {
			this.orderBy = orderBy;
			this.order = order;
		}
		
		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
		}
		
		public String getOrderBy() {
			return orderBy;
		}
		
		public void setOrder(String order) {
			this.order = order;
		}
		
		public String getOrder() {
			return order;
		}
		
	}

	@Override
	public Iterator<OrderEntry> iterator() {
		return orders.iterator();
	}
}
