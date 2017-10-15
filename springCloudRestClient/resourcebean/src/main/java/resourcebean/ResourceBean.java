package resourcebean;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="request")
public class ResourceBean {

	@Id
	private String id;
	@Indexed(direction=IndexDirection.ASCENDING,unique=true)
	private String tag;
	@Indexed(direction=IndexDirection.ASCENDING)
	private String url;
	private String requestBody,headers;

	@Transient
	private String responseBody;
	private int counts;
	@Indexed(direction=IndexDirection.DESCENDING)
	private long lastExecutionDate;
	
	@Transient
	private String statusCodeAndReason;
	
	
	private String requestType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getStatusCodeAndReason() {
		return statusCodeAndReason;
	}
	public void setStatusCodeAndReason(String statusCodeAndReason) {
		this.statusCodeAndReason = statusCodeAndReason;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public long getLastExecutionDate() {
		return lastExecutionDate;
	}
	public void setLastExecutionDate(long lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
	
	
	
	
	
}
