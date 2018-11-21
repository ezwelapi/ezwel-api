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
@APIType
public class CallableExecutor {

	private static final Logger logger = LoggerFactory.getLogger(CallableExecutor.class);
	
	private ExecutorService executor;
	
	private List<Future<?>> result;
	
	public CallableExecutor() {}
	
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
	
	public <T> Future<T> call(Callable<T> task) {
		return executor.submit(task);
	}
	
	public boolean addCall(Callable<?> task) {
		return addResult(call(task));
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
