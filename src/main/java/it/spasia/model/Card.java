package it.spasia.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String uid;
    private String password;
    private Integer timeout;
    private String note;

    private String rele_1;
    private String rele_2;
    private String rele_3;
    private String rele_4;
    private String rele_5;
    private String rele_6;
    private String rele_7;
    private String rele_8;
    private String rele_9;
    private String rele_10;
    private String rele_11;
    private String rele_12;
    private String rele_13;
    private String rele_14;
    private String rele_15;
    private String rele_16;

    public Card() {
    }

    public Card(Integer id, String name, String uid, String password, Integer timeout, String note,
                String rele_1, String rele_2, String rele_3, String rele_4,
                String rele_5, String rele_6, String rele_7, String rele_8,
                String rele_9, String rele_10, String rele_11, String rele_12,
                String rele_13, String rele_14, String rele_15, String rele_16) {
        this.id = id;
        this.name = name;
        this.uid = uid;
        this.password = password;
        this.timeout = timeout;
        this.note = note;
        this.rele_1 = rele_1;
        this.rele_2 = rele_2;
        this.rele_3 = rele_3;
        this.rele_4 = rele_4;
        this.rele_5 = rele_5;
        this.rele_6 = rele_6;
        this.rele_7 = rele_7;
        this.rele_8 = rele_8;
        this.rele_9 = rele_9;
        this.rele_10 = rele_10;
        this.rele_11 = rele_11;
        this.rele_12 = rele_12;
        this.rele_13 = rele_13;
        this.rele_14 = rele_14;
        this.rele_15 = rele_15;
        this.rele_16 = rele_16;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getRele_1() {
        return rele_1;
    }

    public void setRele_1(String rele_1) {
        this.rele_1 = rele_1;
    }

    public String getRele_2() {
        return rele_2;
    }

    public void setRele_2(String rele_2) {
        this.rele_2 = rele_2;
    }

    public String getRele_3() {
        return rele_3;
    }

    public void setRele_3(String rele_3) {
        this.rele_3 = rele_3;
    }

    public String getRele_4() {
        return rele_4;
    }

    public void setRele_4(String rele_4) {
        this.rele_4 = rele_4;
    }

    public String getRele_5() {
        return rele_5;
    }

    public void setRele_5(String rele_5) {
        this.rele_5 = rele_5;
    }

    public String getRele_6() {
        return rele_6;
    }

    public void setRele_6(String rele_6) {
        this.rele_6 = rele_6;
    }

    public String getRele_7() {
        return rele_7;
    }

    public void setRele_7(String rele_7) {
        this.rele_7 = rele_7;
    }

    public String getRele_8() {
        return rele_8;
    }

    public void setRele_8(String rele_8) {
        this.rele_8 = rele_8;
    }

    public String getRele_9() {
        return rele_9;
    }

    public void setRele_9(String rele_9) {
        this.rele_9 = rele_9;
    }

    public String getRele_10() {
        return rele_10;
    }

    public void setRele_10(String rele_10) {
        this.rele_10 = rele_10;
    }

    public String getRele_11() {
        return rele_11;
    }

    public void setRele_11(String rele_11) {
        this.rele_11 = rele_11;
    }

    public String getRele_12() {
        return rele_12;
    }

    public void setRele_12(String rele_12) {
        this.rele_12 = rele_12;
    }

    public String getRele_13() {
        return rele_13;
    }

    public void setRele_13(String rele_13) {
        this.rele_13 = rele_13;
    }

    public String getRele_14() {
        return rele_14;
    }

    public void setRele_14(String rele_14) {
        this.rele_14 = rele_14;
    }

    public String getRele_15() {
        return rele_15;
    }

    public void setRele_15(String rele_15) {
        this.rele_15 = rele_15;
    }

    public String getRele_16() {
        return rele_16;
    }

    public void setRele_16(String rele_16) {
        this.rele_16 = rele_16;
    }

    public String getNote() {
        return note;
    }

    public Card setNote(String note) {
        this.note = note;
        return this;
    }

    public String getReleValue(int i) {
        try {
            Method declaredMethod = Card.class.getDeclaredMethod("getRele_" + i);
            return (String) declaredMethod.invoke(this);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }
}
