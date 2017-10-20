# wiki-app - Getting to Philosophy

This is a full-stack app built using Spring MVC. It takes a Wikipedia URL as input, and displays the total hops and path taken from clicking the first link of each page until Philosophy article is reached and then saves this information in PostgreSQL database.

Following the chain consists of:
1. Clicking on the first non-parenthesized, non-italicized link.
2. Ignoring external links, links to the current page, or red links (links to non-existent pages).
3. Stopping when reaching "Philosophy", a page with no links or a page that does not exist, or when a loop occurs.

This app can be seen in action at https://sk-wiki-app.herokuapp.com/.

This app is built by following best practices.
