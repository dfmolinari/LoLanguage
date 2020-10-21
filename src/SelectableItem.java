public class SelectableItem {
    private String m_key;
    private String m_value;

    public SelectableItem(String key, String value)
    {
        m_key = key;
        m_value = value;
    }

    @Override
    public String toString()
    {
        return m_key;
    }

    public String GetKey()
    {
        return m_key;
    }

    public String GetValue()
    {
        return m_value;
    }
}