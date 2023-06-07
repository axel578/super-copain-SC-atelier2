package com.cpe.cardgame.utils;

public class FilterBy {

    public static final FilterBy NONE = new FilterBy(Boolean.FALSE);
    public static final String CROISSANT = "asc";
    public static final String DECROISSANT = "desc";

    public FilterBy()
    {

    }

    public FilterBy(boolean isActive)
    {
        setUseFilter(isActive);
    }
    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }

    private boolean useFilter = true;
    private String search;

    private int index = 0;
    private int size = 0;

    private String order_by = FilterBy.CROISSANT;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FilterBy other = (FilterBy) obj;
        return useFilter == other.useFilter;
    }
}