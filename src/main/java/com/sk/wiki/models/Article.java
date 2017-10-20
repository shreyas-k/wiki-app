package com.sk.wiki.models;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sk.wiki.models.common.contracts.IEntity;

@Entity
@Table(name = "article")
public class Article implements IEntity<Long>, Serializable {
	private static final long serialVersionUID = -655847955436473794L;
	private static final Integer MAX_DESCRIPTION_LENGTH = 225;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 100)
	private String name;

	@NotNull
	@URL
	@Column(unique = true)
	private String url;

	@Column(name = "short_description")
	private String shortDescription;

	@Transient
	private Elements paras;

	public Article() {
	}

	public Article(final String url, String name, final Elements paras) throws IOException {
		this.url = url;
		if (name == null) {
			name = getLastPathSegment(url);
		} else {
			this.name = name;
		}
		this.paras = paras;
		setShortDescription(paras);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(final String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Elements getParas() {
		return paras;
	}

	public void setParas(final Elements paras) {
		this.paras = paras;
	}

	public void setShortDescription(final Elements paras) {
		final StringBuilder builder = new StringBuilder();
		for (final Element para : paras) {
			final int availableLength = MAX_DESCRIPTION_LENGTH - builder.toString().length();
			if (availableLength > 0) {
				final int copyUnitl = para.text().length() < availableLength ? para.text().length() : availableLength;
				builder.append(para.text().substring(0, copyUnitl));
			} else {
				break;
			}
		}
		shortDescription = builder.toString();
	}

	private String getLastPathSegment(final String url) {
		return url.replaceFirst(".*/([^/?]+).*", "$1");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Article other = (Article) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shortDescription == null) {
			if (other.shortDescription != null)
				return false;
		} else if (!shortDescription.equals(other.shortDescription))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", url=" + url + ", shortDescription=" + shortDescription + "]";
	}

}
