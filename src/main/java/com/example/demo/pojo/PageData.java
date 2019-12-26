package com.example.demo.pojo;

import java.util.List;

public class PageData
{
	private List<?> rows;
	private List<?> footer;
	private long total;

	public PageData() {

	}

	public PageData(List<?> rows, long total)
	{
		this.rows = rows;
		this.total = total;
	}

	public PageData(List<?> rows, long total, List<?> footer)
	{
		this.rows = rows;
		this.total = total;
		this.footer = footer;
	}

	public List<?> getRows()
	{
		return rows;
	}

	public void setRows(List<?> rows)
	{
		this.rows = rows;
	}

	public List<?> getFooter()
	{
		return footer;
	}

	public void setFooter(List<?> footer)
	{
		this.footer = footer;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
