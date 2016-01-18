/**
 * 
 *
 */
package com.xsyi.mongo.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xsyi.core.annotation.MongodbFiledType;


/**
 * Description: <p>基础对象继承类</p>
 * Content Desc:<p>
 * <p>
 *  持久化基类，采用java.lang.ref包的内省机制覆盖父类的toString()、equals(Object o)、hasCode()方法<br/>
 *  hibernate持久层调用的方法
 * </p>
 *<p>
 * @author tangtao7481
 * @version , Feb 7, 2013 2:37:01 PM
 * 
 */
@SuppressWarnings("serial")
public abstract class MongoBaseObject implements Serializable {

    //写入mongodb时，忽略不写入；从Mongodb读取数据的时候会设置该值
    @Field(value="_id")
    @MongodbFiledType(type=MongodbFiledType.Types.IGNORE)
    private ObjectId objectId;
    
    @Field(value="created")
    @MongodbFiledType(type=MongodbFiledType.Types.INT)
    private String created; // int创建时间unix_timestamp

    @Field(value="updated")
    @MongodbFiledType(type=MongodbFiledType.Types.INT)
    private String updated; // int更新时间unix_timestamp
    
    @Field(value="origin_id")
    private ObjectId originId;//原始ObjectId,如果是发生变更的统计数据，此处记录变更前记录的数据
    
    /**
     * 覆盖父类的toString()
     * @return POJO子类详细信息
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * 覆盖父类的equals(Object o)方法
     */
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }
    
    /**
     * 覆盖父类的hasCode()方法
     */
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return the updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }


    /**
     * Getter method for property <tt>originId</tt>.
     * 
     * @return property value of originId
     */
    public ObjectId getOriginId() {
        return originId;
    }

    /**
     * Setter method for property <tt>originId</tt>.
     * 
     * @param originId value to be assigned to property originId
     */
    public void setOriginId(ObjectId originId) {
        this.originId = originId;
    }

    /**
     * Getter method for property <tt>objectId</tt>.
     * 
     * @return property value of objectId
     */
    public ObjectId getObjectId() {
        return objectId;
    }

    /**
     * Setter method for property <tt>objectId</tt>.
     * 
     * @param objectId value to be assigned to property objectId
     */
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    
}
