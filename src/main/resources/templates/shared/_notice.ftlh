<% if flash[:notice] %>
  <div class="flash notice" onclick="this.style.display = 'none'">
    <span style="float: right; margin-right: 10px; pointer-events: none">close</span>
    <%= flash[:notice] %>
  </div>
<% end %>

<% if flash[:error] %>
  <div class="flash notice error" onclick="this.style.display = 'none'">
    <span style="float: right; margin-right: 10px; pointer-events: none">close</span>
    <%= flash[:error] %>
  </div>
<% end %>
