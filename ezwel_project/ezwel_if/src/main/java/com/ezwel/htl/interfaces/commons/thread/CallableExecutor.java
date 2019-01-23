package com.ezwel.htl.interfaces.commons.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;

/**
 * <pre>
 * 주어진 개수만큼의 쓰레드를 갖는 쓰레드풀을 생성하고 
 * 생성한 쓰레드풀에 각 쓰레드별 실행할 Callable.call을 등록하여 
 * 등록된 개수 만큼 쓰레드별 작업을 수행하는 CallableExecutor 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 8.
 * @serviceType API
 */
@APIType(description="멈티쓰레드 풀 생성 및 실행 유틸")
public class CallableExecutor {

	private static final Logger logger = LoggerFactory.getLogger(CallableExecutor.class);
	
	private ExecutorService executor;
	
	private List<Future<?>> result;
	
	public void initThreadPool(int poolCount) {
		if(executor != null) {
			this.clear();
		}
		if(poolCount < 1) {
			throw new APIException("Threads number can not be smaller than one. '{}'", Integer.toString(poolCount));
		}
		executor = Executors.newFixedThreadPool(poolCount);
		result = new ArrayList<Future<?>>();
	}
	
	public <T> Future<T> call(Callable<T> task, Long sleepMillis) {
		Future<T> future = null;
		try {

			future = executor.submit(task);
			
			if(sleepMillis != null && sleepMillis > OperateConstants.LONG_ZERO_VALUE) {
				Thread.sleep(sleepMillis);
			}
		} catch (InterruptedException e) {
			logger.error("[CallableExecutor Call]", e);
		} catch (Exception e) {
			logger.error("[CallableExecutor Call]", e);
		}
		return future;
	}
	
	public boolean addCall(Callable<?> task) {
		return addCall(task, null);
	}
	
	public boolean addCall(Callable<?> task, Long sleepMillis) {
		return addResult(call(task, sleepMillis));
	}
	
	public boolean addResult(Future<?> in){
		return result.add(in);
	}
	
	public List<Future<?>> getResult() {
		return result;
	}
	
	public void clear(){
		if(result != null) {
			result.clear();
		}
		if(executor != null && !executor.isShutdown()) {
			executor.shutdown();
		}
	}
}
