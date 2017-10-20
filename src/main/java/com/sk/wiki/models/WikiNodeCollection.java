package com.sk.wiki.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.jsoup.nodes.Node;

public class WikiNodeCollection implements Iterable<Node> {
	private Node root;

	public WikiNodeCollection(final Node root) {
		this.root = root;
	}

	@Override
	public Iterator<Node> iterator() {
		return new Iter(root);
	}

	private class Iter implements Iterator<Node> {
		Stack<Node> stack;

		public Iter(final Node node) {
			stack = new Stack<>();
			stack.push(node);
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public Node next() {
			if (stack.isEmpty()) {
				throw new NoSuchElementException();
			}
			final Node node = stack.pop();
			final List<Node> nodes = new ArrayList<Node>(node.childNodes());
			Collections.reverse(nodes);
			if (node.childNodeSize() > 0) {
				stack.addAll(nodes);
			}
			return node;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
