/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.frc.thrift.datatype;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-01-05")
public class ResListStr implements org.apache.thrift.TBase<ResListStr, ResListStr._Fields>, java.io.Serializable, Cloneable, Comparable<ResListStr> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ResListStr");

  private static final org.apache.thrift.protocol.TField RES_FIELD_DESC = new org.apache.thrift.protocol.TField("res", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("value", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField EXT_FIELD_DESC = new org.apache.thrift.protocol.TField("ext", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ResListStrStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ResListStrTupleSchemeFactory());
  }

  public int res; // required
  public List<String> value; // required
  public String ext; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RES((short)1, "res"),
    VALUE((short)2, "value"),
    EXT((short)3, "ext");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // RES
          return RES;
        case 2: // VALUE
          return VALUE;
        case 3: // EXT
          return EXT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __RES_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RES, new org.apache.thrift.meta_data.FieldMetaData("res", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VALUE, new org.apache.thrift.meta_data.FieldMetaData("value", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.EXT, new org.apache.thrift.meta_data.FieldMetaData("ext", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ResListStr.class, metaDataMap);
  }

  public ResListStr() {
  }

  public ResListStr(
    int res,
    List<String> value,
    String ext)
  {
    this();
    this.res = res;
    setResIsSet(true);
    this.value = value;
    this.ext = ext;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ResListStr(ResListStr other) {
    __isset_bitfield = other.__isset_bitfield;
    this.res = other.res;
    if (other.isSetValue()) {
      List<String> __this__value = new ArrayList<String>(other.value);
      this.value = __this__value;
    }
    if (other.isSetExt()) {
      this.ext = other.ext;
    }
  }

  public ResListStr deepCopy() {
    return new ResListStr(this);
  }

  @Override
  public void clear() {
    setResIsSet(false);
    this.res = 0;
    this.value = null;
    this.ext = null;
  }

  public int getRes() {
    return this.res;
  }

  public ResListStr setRes(int res) {
    this.res = res;
    setResIsSet(true);
    return this;
  }

  public void unsetRes() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RES_ISSET_ID);
  }

  /** Returns true if field res is set (has been assigned a value) and false otherwise */
  public boolean isSetRes() {
    return EncodingUtils.testBit(__isset_bitfield, __RES_ISSET_ID);
  }

  public void setResIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RES_ISSET_ID, value);
  }

  public int getValueSize() {
    return (this.value == null) ? 0 : this.value.size();
  }

  public java.util.Iterator<String> getValueIterator() {
    return (this.value == null) ? null : this.value.iterator();
  }

  public void addToValue(String elem) {
    if (this.value == null) {
      this.value = new ArrayList<String>();
    }
    this.value.add(elem);
  }

  public List<String> getValue() {
    return this.value;
  }

  public ResListStr setValue(List<String> value) {
    this.value = value;
    return this;
  }

  public void unsetValue() {
    this.value = null;
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean isSetValue() {
    return this.value != null;
  }

  public void setValueIsSet(boolean value) {
    if (!value) {
      this.value = null;
    }
  }

  public String getExt() {
    return this.ext;
  }

  public ResListStr setExt(String ext) {
    this.ext = ext;
    return this;
  }

  public void unsetExt() {
    this.ext = null;
  }

  /** Returns true if field ext is set (has been assigned a value) and false otherwise */
  public boolean isSetExt() {
    return this.ext != null;
  }

  public void setExtIsSet(boolean value) {
    if (!value) {
      this.ext = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RES:
      if (value == null) {
        unsetRes();
      } else {
        setRes((Integer)value);
      }
      break;

    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((List<String>)value);
      }
      break;

    case EXT:
      if (value == null) {
        unsetExt();
      } else {
        setExt((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RES:
      return getRes();

    case VALUE:
      return getValue();

    case EXT:
      return getExt();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RES:
      return isSetRes();
    case VALUE:
      return isSetValue();
    case EXT:
      return isSetExt();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ResListStr)
      return this.equals((ResListStr)that);
    return false;
  }

  public boolean equals(ResListStr that) {
    if (that == null)
      return false;

    boolean this_present_res = true;
    boolean that_present_res = true;
    if (this_present_res || that_present_res) {
      if (!(this_present_res && that_present_res))
        return false;
      if (this.res != that.res)
        return false;
    }

    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (!this.value.equals(that.value))
        return false;
    }

    boolean this_present_ext = true && this.isSetExt();
    boolean that_present_ext = true && that.isSetExt();
    if (this_present_ext || that_present_ext) {
      if (!(this_present_ext && that_present_ext))
        return false;
      if (!this.ext.equals(that.ext))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_res = true;
    list.add(present_res);
    if (present_res)
      list.add(res);

    boolean present_value = true && (isSetValue());
    list.add(present_value);
    if (present_value)
      list.add(value);

    boolean present_ext = true && (isSetExt());
    list.add(present_ext);
    if (present_ext)
      list.add(ext);

    return list.hashCode();
  }

  @Override
  public int compareTo(ResListStr other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRes()).compareTo(other.isSetRes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.res, other.res);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValue()).compareTo(other.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.value, other.value);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExt()).compareTo(other.isSetExt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ext, other.ext);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ResListStr(");
    boolean first = true;

    sb.append("res:");
    sb.append(this.res);
    first = false;
    if (!first) sb.append(", ");
    sb.append("value:");
    if (this.value == null) {
      sb.append("null");
    } else {
      sb.append(this.value);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ext:");
    if (this.ext == null) {
      sb.append("null");
    } else {
      sb.append(this.ext);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ResListStrStandardSchemeFactory implements SchemeFactory {
    public ResListStrStandardScheme getScheme() {
      return new ResListStrStandardScheme();
    }
  }

  private static class ResListStrStandardScheme extends StandardScheme<ResListStr> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ResListStr struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RES
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.res = iprot.readI32();
              struct.setResIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.value = new ArrayList<String>(_list0.size);
                String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.value.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ext = iprot.readString();
              struct.setExtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ResListStr struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(RES_FIELD_DESC);
      oprot.writeI32(struct.res);
      oprot.writeFieldEnd();
      if (struct.value != null) {
        oprot.writeFieldBegin(VALUE_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.value.size()));
          for (String _iter3 : struct.value)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.ext != null) {
        oprot.writeFieldBegin(EXT_FIELD_DESC);
        oprot.writeString(struct.ext);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ResListStrTupleSchemeFactory implements SchemeFactory {
    public ResListStrTupleScheme getScheme() {
      return new ResListStrTupleScheme();
    }
  }

  private static class ResListStrTupleScheme extends TupleScheme<ResListStr> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ResListStr struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRes()) {
        optionals.set(0);
      }
      if (struct.isSetValue()) {
        optionals.set(1);
      }
      if (struct.isSetExt()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetRes()) {
        oprot.writeI32(struct.res);
      }
      if (struct.isSetValue()) {
        {
          oprot.writeI32(struct.value.size());
          for (String _iter4 : struct.value)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetExt()) {
        oprot.writeString(struct.ext);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ResListStr struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.res = iprot.readI32();
        struct.setResIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.value = new ArrayList<String>(_list5.size);
          String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.value.add(_elem6);
          }
        }
        struct.setValueIsSet(true);
      }
      if (incoming.get(2)) {
        struct.ext = iprot.readString();
        struct.setExtIsSet(true);
      }
    }
  }

}

