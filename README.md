# wiki-app - Getting to Philosophy

This is a full-stack app built using Spring MVC. It takes Wikipedia URL as input, displays the total hops and path taken to reach  Philosophy page by clicking the first valid link on each page and then saves this information to PostgreSQL database.

However there are a few conditions, link is not valid if:
1. Parenthesized or italicized.
2. External link, links to current page, or red links (links to non-existent pages).
3. A page with no links or a page that does not exist, or when loop occurs.

This app can be seen in action at https://sk-wiki-app.herokuapp.com/.

This app is built by following best practices.
