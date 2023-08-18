
<header>
  <div class="container">
    <a href="<%= play_url %>" class="title-nav">
      <span class="logo"></span>
      <span>Onikai</span>
    </a>
    <div class="flex-span"></div>
    <div class="actions">
      <% if user != nil %><%= user.username %>
      <% else %> Not Signed In <% end %>
    </div>
  </div>
</header>