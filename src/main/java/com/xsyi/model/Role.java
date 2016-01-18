package com.xsyi.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Role {
	
	public Role(String roleId,String roleName){
		this.roleid=roleId;
		this.rolename=roleName;
	}
	
	public Role(){
	}
	
    @Override
	public boolean equals(Object o) {
    	if(o == null){
    		return false;
    	}
    	if(o == this){
    		return true;
    	}
    	if(o.getClass() != getClass()){
    		return false;
    	}
    	Role r=(Role) o;
		return new EqualsBuilder().append(Integer.parseInt(getRoleid()),Integer.parseInt(r.getRoleid())).isEquals();
	}

	@Override
	public int hashCode() {
		final int prime=31;
		return new HashCodeBuilder(Integer.parseInt(getRoleid()), prime).toHashCode();
	}

    private String roleid;

    private String rolename;
    //备注
    private String memo;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
    
}