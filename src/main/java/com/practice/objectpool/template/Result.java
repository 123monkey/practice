package com.practice.objectpool.template;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Map;

public class Result<T> {
	// 请求状态
	private int status;
	// 提示信息
	private String message;
	// 真正的结果
	private T result;
    // 异常
    private Exception exception;


	/**
	 * 根据@Status 获取结果
	 * 
	 * @param status
	 * @return
	 */
	public static <T> Result<T> getResult(Status status) {
		return new Result<T>()
				.setStatus(status.getKey())
				.setMessage(status.getValue());
	}


    /**
	 * 获取正常返回结果
	 * 
	 * @param result
	 * @return
	 */
    public static <T> Result<T> getResult(Object result) {
		if (result == null) {
			return getResult(Status.NO_RESULT);
		}
		return (Result<T>) getResult(Status.OK).setResult(result);
	}

	
	/**
     * 获取Db异常返回结果
     * @return
     */
    public static <T> Result<T> getDBErrorResult(Exception e) {
        return (Result<T>) getResult(Status.DB_ERROR).setException(e);
    }

    
    /**
     * 外部可以使用此方法便捷的判断结果是否正确
     * @return
     */
    @JsonIgnore
    public boolean isOK() {
        return Status.OK.getKey() == status;
    }
    
    /**
     * 外部可以使用此方法便捷的判断结果是否正确
     * @return
     */
    @JsonIgnore
    public boolean isNotOK() {
        return !isOK();
    }

    /**
     * 如果结果是Collection，判断是否有数据
     * 
     * @return
     */
    @JsonIgnore
    @SuppressWarnings("rawtypes")
    public boolean isNotEmpty() {
        if(isNotOK()) {
            return false;
        }
        if(result instanceof Collection && ((Collection) result).size() > 0){
            return true;
        }
        if(result instanceof Map && ((Map) result).size() > 0){
            return true;
        }
        return false;
    }

    /**
     * 如果结果是Collection，判断是否有数据
     * 
     * @return
     */
    @JsonIgnore
    public boolean isEmpty() {
        return !isNotEmpty();
    }

	public Result<T> setStatus(int status) {
		this.status = status;
		return this;
	}


	public T getResult() {
		return result;
	}

	public Result<T> setResult(T result) {
		this.result = result;
		return this;
	}

    public Exception getException() {
        return exception;
    }

    public Result<T> setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", message=" + message + ", result=" + result + ", exception=" + exception
                + ", isOK()=" + isOK() + "]";
    }
}
