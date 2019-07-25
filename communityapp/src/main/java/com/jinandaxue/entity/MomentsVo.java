package com.jinandaxue.entity;


public class MomentsVo {
	private String nickname;
	private String headlogo;
	private Integer id;

	private String content;

	private String logo;

	private Integer uid;
	private String loved;

	private String comments;
	private String createtime;

	private String goodsimage;
	private String goodsname;
	private String goodsdescribe;

	private int type;
	private int goodsid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo == null ? null : logo.trim();
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadlogo() {
		return headlogo;
	}
	public void setHeadlogo(String headlogo) {
		this.headlogo = headlogo;
	}

	public String getGoodsimage() {
		return goodsimage;
	}

	public void setGoodsimage(String goodsimage) {
		this.goodsimage = goodsimage;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsdescribe() {
		return goodsdescribe;
	}

	public void setGoodsdescribe(String goodsdescribe) {
		this.goodsdescribe = goodsdescribe;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}

	public String getLoved() {
		return loved;
	}

	public void setLoved(String loved) {
		this.loved = loved;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
