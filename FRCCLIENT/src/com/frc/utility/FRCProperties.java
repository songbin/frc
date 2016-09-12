package com.frc.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author songbin
 */
public class FRCProperties {
    private ArrayList<Property> m_contents = new ArrayList<Property>();
    private HashMap<String, Property> m_nvmap = new HashMap<String, Property>();

    private String getClassName() {
	return "FRCProperties";
    }

    /**
     * 读入配置文件
     * 
     * @param is
     * @throws Exception
     */
    public boolean loadConfig(String configFile) {
	String flag = this.getClassName() + "loadConfig";
	if (m_contents.size() > 0)
	    return true; // already loaded yet...
	try {
	    // InputStream inputStream =
	    // Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
	    // //
	    // Thread.currentThread().getContextClassLoader().getResource(name)
	    // if (inputStream == null)
	    // return false;
	    // load(inputStream);
	    load(new FileInputStream(configFile));
	} catch (IOException e) {
	    FRCLogger
		    .getInstance()
		    .warn(IdGen.getId(),
			    flag,
			    "exception:Load configure file: {} fail,detail informaiton ",
			    e, configFile);
	    return false;
	}
	return true;

    }

    /**
     * 通过数据库读入配置文件
     * 
     * @param is
     * @throws IOException
     * @throws SQLException
     */
    public void loadConfigFromDB() throws IOException, SQLException {
	// if (m_contents.size() > 0) return; // already loaded yet...
	/*
	 * ArrayList<Config.Property> list =
	 * Db.loadConfig(ConfigKey.DB_CONFIG_CATEGORY_ALL); for (int i=0;
	 * i<list.size(); i++) { Property p = list.get(i); m_contents.add(p);
	 * m_nvmap.put(p.getKey(), p); }
	 */
    }

    /**
     * 从 FileInputStream 读入配置信息
     * 
     * @param is
     * @throws IOException
     */
    private void load(InputStream is) throws IOException {
	BufferedReader in = new BufferedReader(new InputStreamReader(is));
	String line = null;
	int lineNumber = 0;
	while ((line = in.readLine()) != null) {
	    Property pl = new Property(line, lineNumber);
	    m_contents.add(pl);
	    if (pl.hasKeyValue()) {
		m_nvmap.put(pl.getKey(), pl);
	    }
	    lineNumber++;
	}

    }

    /**
     * 保存配置文件
     * 
     * @throws IOException
     */
    public void save(FileOutputStream os) throws IOException {
	for (Property pl : m_contents) {
	    String strToWrite = pl.toString() + "\n";
	    os.write(strToWrite.getBytes());
	}
    }

    /**
     * 保存配置
     * 
     * @param key
     * @param value
     */
    private void put(String key, String value) {
	Property pl = m_nvmap.get(key);
	if (pl != null) {
	    pl.setValue(value);
	} else {
	    pl = new Property(String.format("%s = %s", key, value),
		    m_contents.size());
	    m_contents.add(pl);
	    m_nvmap.put(key, pl);
	}
    }

    /**
     * 得到所有的 key 值
     * 
     * @return
     */
    public Set<String> getKeys() {
	return m_nvmap.keySet();
    }

    /**
     * 获取一个值，如果不存在，则返回 null
     * 
     * @param key
     * @return
     */
    private String getValue(String key) {
	Property pl = m_nvmap.get(key);
	if (pl != null)
	    return pl.getValue();
	else
	    return null;
    }

    /**
     * 更新一个 key - value 对，会保存 value.toString() 的值
     * 
     * @param key
     * @param value
     */
    public void update(String key, Object value) {
	put(key, value.toString());
    }

    /**
     * 设置一个 key - value 对，会保存 value.toString() 的值
     * 
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
	update(key, value);
    }

    /**
     * 设置一个 key - value 对
     * 
     * @param key
     * @param value
     */
    public void update(String key, int value) {
	put(key, String.valueOf(value));
    }

    /**
     * 设置一个 key - int 对
     * 
     * @param key
     * @param value
     */
    public void setInt(String key, int value) {
	update(key, value);
    }

    public void setLong(String key, long value) {
	update(key, value);
    }

    /**
     * 设置一个 key - value 对
     * 
     * @param key
     * @param value
     */
    public void update(String key, double value) {
	put(key, String.valueOf(value));
    }

    /**
     * 更新一个 long 值
     * 
     * @param key
     * @param value
     */
    public void update(String key, long value) {
	put(key, String.valueOf(value));
    }

    /**
     * 设置一个 long 值
     * 
     * @param key
     * @param value
     */
    public void setDouble(String key, double value) {
	update(key, value);
    }

    /**
     * 更新一个字符串值
     * 
     * @param key
     * @param value
     */
    public void update(String key, String value) {
	put(key, value);
    }

    /**
     * 设置一个字符串值
     * 
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
	update(key, value);
    }

    /**
     * 更新一个 boolean 值。实际写到配置中时，采用的是整数：true - 1，false - 0
     * 
     * @param key
     * @param value
     */
    public void update(String key, boolean value) {
	put(key, String.valueOf(value ? 1 : 0));
    }

    /**
     * 设置一个 boolean 值。实际写到配置中时，采用的是整数：true - 1，false - 0
     * 
     * @param key
     * @param value
     */
    public void setBoolean(String key, boolean value) {
	update(key, value);
    }

    /**
     * 获取配置，如果不存在，则返回 null
     * 
     * @param key
     * @return
     */
    public Object get(String key) {
	return getValue(key);
    }

    /**
     * 获取一个字符串格式的配置，如果不存在，则返回空字符串（""）
     * 
     * @param key
     * @return
     */
    public String getString(String key) {
	return getString(key, "");
    }

    /**
     * 获取一个字符串，如果不存在，则返回 defaultValue
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
	Object o = getValue(key);
	if (o == null)
	    return defaultValue;
	else
	    return o.toString();
    }

    /**
     * 获取一个 boolean，如果不存在或格式错误，则返回 false
     * 
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
	return getBoolean(key, false);
    }

    /**
     * 获取一个 boolean，如果不存在或格式错误，则返回 defaultValue
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue) {
	int ret = getInt(key, defaultValue ? 1 : 0);
	return (ret == 1) ? true : false;
    }

    /**
     * 获取一个 double，如果不存在或格式错误，则返回 0
     * 
     * @param key
     * @return
     */
    public double getDouble(String key) {
	return getDouble(key, 0.0);
    }

    /**
     * 获取一个 double，如果不存在或格式错误，则返回 defaultValue
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public double getDouble(String key, double defaultValue) {
	Object o = getValue(key);
	if (o == null)
	    return defaultValue;
	else {
	    try {
		return Double.parseDouble(o.toString());
	    } catch (NumberFormatException e) {
		return defaultValue;
	    }
	}
    }

    /**
     * 获取一个 int，如果不存在或格式错误，则返回 0
     * 
     * @param key
     * @return
     */
    public int getInt(String key) {
	return getInt(key, 0);
    }

    /**
     * 获取一个 int，如果不存在或格式错误，则返回 defaultValue
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public int getInt(String key, int defaultValue) {
	Object o = getValue(key);
	if (o == null)
	    return defaultValue;
	else {
	    try {
		return (int) Double.parseDouble(o.toString());
		// return Integer.parseInt(o.toString());
	    } catch (NumberFormatException e) {
		return defaultValue;
	    }
	}
    }

    /**
     * 获取一个 long，如果不存在或格式错误，则返回 0
     * 
     * @param key
     * @return
     */
    public long getLong(String key) {
	return getLong(key, 0);
    }

    /**
     * 获取一个 int，如果不存在或格式错误，则返回 defaultValue
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLong(String key, long defaultValue) {
	Object o = getValue(key);
	if (o == null)
	    return defaultValue;
	try {
	    return Long.parseLong(o.toString());
	} catch (NumberFormatException e) {
	    return defaultValue;
	}
    }

    /**
     * 移除一个配置
     * 
     * @param key
     */
    public void remove(String key) {
	Property p = m_nvmap.get(key);
	if (p != null) {
	    m_nvmap.remove(key);
	    m_contents.remove(p);
	}
    }

    /**
     * 代表了配置文件中的一行。格式：name = value # comments
     * 
     * @author
     */
    public static class Property {
	private int m_lineNumber = -1; // 行号
	private String m_contents = null; // 行内容
	private String m_key = null; // key
	private String m_value = null; // value
	private String m_comments = ""; // 行末的注释

	public Property(String key, String value, int lineNumber) {
	    m_key = key;
	    m_value = value;
	    m_lineNumber = lineNumber;
	    m_contents = key + "=" + value;
	}

	public Property(String line, int lineNumber) {
	    m_contents = line;
	    m_lineNumber = lineNumber;
	    int idx = -1;
	    if ((idx = line.indexOf('#')) >= 0) {
		m_comments = line.substring(idx); // m_comments includes '#'
		line = line.substring(0, idx);
	    }

	    if ((idx = line.indexOf('=')) > 0) {
		m_key = line.substring(0, idx).trim();
		if (line.length() > (idx + 1))
		    m_value = line.substring(idx + 1).trim();

		if (isEmpty(m_value))
		    m_value = null;
	    }
	}

	private boolean isEmpty(String str) {
	    return (str == null) || (str.length() == 0);
	}

	@Override
	public String toString() {
	    return m_contents;
	}

	public boolean hasKeyValue() {
	    return !isEmpty(m_key);
	}

	public String getValue() {
	    return m_value;
	}

	public void setValue(String value) {
	    m_value = value;

	    if ((m_comments == null) || (m_comments.length() == 0)) {
		m_contents = String.format("%s = %s", m_key, m_value);
	    } else {
		m_contents = String.format("%s = %s \t%s", m_key, m_value,
			m_comments);
	    }
	}

	public int getLineNumber() {
	    return m_lineNumber;
	}

	public String getContents() {
	    return m_contents;
	}

	public String getKey() {
	    return m_key;
	}

	public String getComments() {
	    return m_comments;
	}
    }
}
