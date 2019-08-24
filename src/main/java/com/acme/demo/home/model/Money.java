package com.acme.demo.home.model;

import java.math.BigDecimal;

/**
 *
 * @author Ana Maria
 */
public class Money {

	enum Currency {
		CAD, EUR, USD
	}

	private BigDecimal amount;

	private Currency currency;

	public Money(BigDecimal amount) {
		this(amount, Currency.USD);
	}

	Money(BigDecimal amount, Currency currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}

}