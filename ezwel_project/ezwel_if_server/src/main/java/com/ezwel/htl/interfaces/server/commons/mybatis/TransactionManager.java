package com.ezwel.htl.interfaces.server.commons.mybatis;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

@APIType(description="마이바티스 트랜젝션 관련 유틸")
public class TransactionManager extends DefaultTransactionDefinition {

	private static final long serialVersionUID = 1L;

	private PlatformTransactionManager transactionManager = (PlatformTransactionManager) LApplicationContext.getBean(PlatformTransactionManager.class);

	private TransactionStatus status;

	public void start() throws TransactionException {
		status = transactionManager.getTransaction(this);
	}

	public void commit() throws TransactionException {
		if (!status.isCompleted()) {
			transactionManager.commit(status);
		}
	}

	public void rollback() throws TransactionException {
		if (!status.isCompleted()) {
			transactionManager.rollback(status);
		}
	}

	public void end() throws TransactionException {
		rollback();
	}
	
}