package com.sk.wiki.models;

import java.util.Stack;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class WikiParser {
	private String parentUrl;
	private Elements paragraphs;
	private Stack<String> parensStack;

	public WikiParser(final Elements paragraphs, final String parentUrl) {
		this.parentUrl = parentUrl;
		this.paragraphs = paragraphs;
		parensStack = new Stack<>();
	}

	public Element findNextLink() {
		Element nextLink = null;
		for (final Element paragraph : paragraphs) {
			nextLink = doFindNextLink(paragraph);
			if (nextLink != null) {
				return nextLink;
			}
		}
		return nextLink;
	}

	private Element doFindNextLink(final Node root) {
		final Iterable<Node> nodes = new WikiNodeCollection(root);
		Element nextLink = null;
		for (final Node node : nodes) {
			if (node instanceof TextNode) {
				processTextNode((TextNode) node);
			}
			if (node instanceof Element) {
				nextLink = validateElement((Element) node);
				if (nextLink != null) {
					return nextLink;
				}
			}
		}
		return nextLink;
	}

	private Element validateElement(final Element element) {
		Element nextLink = null;
		if (element.tagName().equals("a")) {
			if (isValidLink(element)) {
				nextLink = element;
			}
		}
		return nextLink;
	}

	private boolean isValidLink(final Element element) {
		// ignore if italicized
		if (isItalicized(element)) {
			return false;
		}
		// ignore if paranthesized
		if (isParanthesized(element)) {
			return false;
		}
		// ignore red links
		if (!startsWith(element, "/wiki") || startsWith(element, "/w/")) {
			return false;
		}
		// ignore bookmark
		if (startsWith(element, "#")) {
			return false;
		}
		// ignore help page
		if (startsWith(element, "/wiki/help")) {
			return false;
		}
		// hack ignore inconsistent coordinate links that are sometimes wrapped inside p tag
		if (startsWith(element, "/wiki/Geographic_coordinate_system")
				&& !parentUrl.contains("/wiki/Geographic_coordinate_system")) {
			return false;
		}
		// ignore pronunciation and non-wiki links
		if (element.text().contains("Greek")) {
			return false;
		}
		if (element.text().contains("Latin")) {
			return false;
		}
		if (element.text().contains("wiktionary")) {
			return false;
		}
		return true;
	}

	private boolean isItalicized(final Element start) {
		// follow the parent chain until we get to null
		for (Element elt = start; elt != null; elt = elt.parent()) {
			if (elt.tagName().equals("i") || elt.tagName().equals("em")) {
				return true;
			}
		}
		return false;
	}

	private boolean isParanthesized(final Element elt) {
		// check whether there are any parentheses on the stack
		return !parensStack.isEmpty();
	}

	private boolean startsWith(final Element elt, final String s) {
		return (elt.attr("href").startsWith(s));
	}

	private void processTextNode(final TextNode node) {

		final String[] parts = node.text().split("()");

		if (parts.length > 0) {
			for (final String part : parts) {
				if (part.equals("(")) {
					parensStack.push(part);
				} else if (part.equals(")")) {
					parensStack.pop();
				}
			}
		}
	}
}
