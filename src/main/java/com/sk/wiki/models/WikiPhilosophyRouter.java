package com.sk.wiki.models;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sk.wiki.models.common.contracts.IEntity;

@Entity
@Table(name = "wiki_philosophy_router")
public class WikiPhilosophyRouter implements IEntity<Long>, Serializable {
	private static final long serialVersionUID = 7842228916581664843L;
	private static final Integer MAX_SEARCH_URLS = 100;

	public WikiPhilosophyRouter() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "source_article_id")
	@NotNull
	private Article sourceArticle;

	@ManyToOne
	@JoinColumn(name = "destination_article_id")
	@NotNull
	private Article destinationArticle;

	@ManyToMany
	@OrderColumn(name = "article_order_index")
	@JoinTable(name = "visited_article", joinColumns = @JoinColumn(name = "wiki_philosophy_route_id"), inverseJoinColumns = @JoinColumn(name = "visited_article_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "wiki_philosophy_route_id", "visited_article_id" }) })
	private List<Article> visitedArticles;

	@Column(name = "total_hops")
	private Integer totalHops;

	@Transient
	private WikiRetriever wikiRetriever;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	public WikiPhilosophyRouter(final String sourceUrl, final String destinationUrl) throws IOException {
		wikiRetriever = new WikiRetriever();
		visitedArticles = new LinkedList<>();

		PageInfo pageInfo = fetchParas(sourceUrl);
		sourceArticle = new Article(sourceUrl, pageInfo.getTitle(), pageInfo.getParas());

		pageInfo = fetchParas(destinationUrl);
		destinationArticle = new Article(destinationUrl, pageInfo.getTitle(), pageInfo.getParas());
	}

	public void computePath() throws IOException {
		int count = 0;
		Article article = sourceArticle;

		while (count != MAX_SEARCH_URLS && !article.equals(destinationArticle)) {
			if (visitedArticles.contains(article)) {
				throw new ValidationException("Sorry. Encountered Loop!");
			} else {
				visitedArticles.add(article);
			}

			final Element nextLink = getValidNextLink(article.getParas(), article.getUrl());

			if (nextLink == null) {
				throw new ValidationException("Encountered page with no valid links");
			}

			final PageInfo pageInfo = fetchParas(nextLink.attr("abs:href"));

			article = new Article(nextLink.attr("abs:href"), pageInfo.getTitle(), pageInfo.getParas());

			count++;
		}
		visitedArticles.add(destinationArticle);
		totalHops = count;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public Article getSourceArticle() {
		return sourceArticle;
	}

	public void setSourceArticle(final Article sourceArticle) {
		this.sourceArticle = sourceArticle;
	}

	public Article getDestinationArticle() {
		return destinationArticle;
	}

	public void setDestinationArticle(final Article destinationArticle) {
		this.destinationArticle = destinationArticle;
	}

	public List<Article> getVisitedArticles() {
		return visitedArticles;
	}

	public void setVisitedArticles(final List<Article> visitedArticles) {
		this.visitedArticles = visitedArticles;
	}

	public Integer getTotalHops() {
		return totalHops;
	}

	public void setTotalHops(final Integer totalHops) {
		this.totalHops = totalHops;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	private PageInfo fetchParas(final String url) throws IOException {
		return wikiRetriever.readWikiPage(url);
	}

	private Element getValidNextLink(final Elements paras, final String url) throws IOException {
		final WikiParser parser = new WikiParser(paras, url);
		return parser.findNextLink();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationArticle == null) ? 0 : destinationArticle.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sourceArticle == null) ? 0 : sourceArticle.hashCode());
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
		final WikiPhilosophyRouter other = (WikiPhilosophyRouter) obj;
		if (destinationArticle == null) {
			if (other.destinationArticle != null)
				return false;
		} else if (!destinationArticle.equals(other.destinationArticle))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sourceArticle == null) {
			if (other.sourceArticle != null)
				return false;
		} else if (!sourceArticle.equals(other.sourceArticle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WikiPhilosophy [sourceArticle=" + sourceArticle + ", destinationArticle=" + destinationArticle
				+ ", visitedArticles=" + visitedArticles + ", totalHops=" + totalHops + ", wikiRetriever="
				+ wikiRetriever + "]";
	}
}
