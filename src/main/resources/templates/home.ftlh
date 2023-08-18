<div class="box game-container">
  <div id="unity-container" class="unity-desktop">
    <div class="canvas-wrapper">
      <canvas id="unity-canvas"></canvas>
    </div>
    <div id="unity-loading-bar">
      <div id="unity-logo"></div>
      <div id="unity-progress-bar-empty">
        <div id="unity-progress-bar-full"></div>
      </div>
    </div>
    <div id="unity-footer">
      <div id="unity-webgl-logo"></div>
      <div id="unity-fullscreen-button"></div>
      <div id="unity-build-title">Onikai</div>
    </div>
  </div>
  <div class="game-info">
    <div class="left">
      <div class="description">
        <figure class="img-action-sword"></figure>
        <figure class="text-description">
          <span class="title">Onikai</span>
          <br>
          <span class="tag-line">A free async turn based strategy game.</span>
          <br>
          <span class="long-description">
            <p>A completely free multiplayer online strategy game.</p>
            <p><br>Play against other players, play locally, complete puzzles, and in the future even create your own puzzles to share with others.</p>
            </span>
        </figure>
      </div>
      <div class="description">
        <figure class="img-action-sword"></figure>
        <div class="text-description how-to-play">
          <span class="title">How To Play</span>
          <ul class="list-no-style">
            <li>
              <span class=section-header>Winning conditions:</span>
              <ul class="list-no-style">
                <li>
                  <figure class="img game-icon img-crystal"></figure>
                  <span>Breaking all your opponent's crystals</span>
                </li>
                <li>
                  <figure class="img game-icon img-ghost"></figure>
                  <span>Vaporizing all of your opponent's units</span>
                </li>
              </ul>
            </li>
            <li>
              <span class=section-header>Tips:</span>
              <ul class="list-no-style">
                <li>
                  <figure class="img game-icon img-action-indicator"></figure>
                  <span>Click the action indictator to <strong>undo</strong> your turn before submitting it</span>
                  <ul style="margin-left:64px">
                    <li>
                      Try out different strategies risk free
                    </li>
                  </ul>
                </li>
                <li>
                  <figure class="img game-icon img-bag"></figure>
                  <span>Right-click your bag to see what items you have left </span>
                  <ul style="margin-left:64px">
                    <li>
                      Right-click <img src="/images/game-icon-bag-aux.png" style="display: inline-block;margin-bottom: -10px;"/> next to your opponents name to see what items they have left
                    </li>
                  </ul>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="right"></div>
  </div>
</div>
<script>
<#noparse>
  var productionBuild = true
  var suffix = productionBuild ? ".unityweb" : "";
  var buildUrl = "build/onikai/Build";
  var loaderUrl = buildUrl + "/onikai.loader.js";
  var config = {
    dataUrl: `${buildUrl}/onikai.data${suffix}`,
    frameworkUrl: `${buildUrl}/onikai.framework.js${suffix}`,
    codeUrl: `${buildUrl}/onikai.wasm${suffix}`,
    streamingAssetsUrl: "StreamingAssets",
    companyName: "DefaultCompany",
    productName: "Onikai",
    productVersion: "1.0.0",
  };

  var container = document.querySelector("#unity-container");
  var canvas = document.querySelector("#unity-canvas");
  var loadingBar = document.querySelector("#unity-loading-bar");
  var progressBarFull = document.querySelector("#unity-progress-bar-full");
  var fullscreenButton = document.querySelector("#unity-fullscreen-button");

  mixpanel.track('Game.Loading.Start')
  mixpanel.time_event('Game.Loading.Completed');

  config.devicePixelRatio = 2;
  if (/iPhone|iPad|iPod|Android/i.test(navigator.userAgent)) {
    container.className = "unity-mobile";
  } else {
    canvas.style.width = "1280px";
    canvas.style.height = "720px";
  }
  loadingBar.style.display = "block";

  var script = document.createElement("script");
  script.src = loaderUrl;
  script.onload = () => {
    createUnityInstance(canvas, config, (progress) => {
      progressBarFull.style.width = 100 * progress + "%";
    }).then((unityInstance) => {
      mixpanel.track('Game.Loading.Completed',{
        width: window.innerWidth,
        height: window.innerHeight,
        devicePixelRatio: window.devicePixelRatio
      });
      window.unityInstance = unityInstance;
      window.unityInstance.Module.devicePixelRatio = window.preferredDevicePixelRatio;
      loadingBar.style.display = "none";
      fullscreenButton.onclick = () => {
        unityInstance.SetFullscreen(1);
      };
    }).catch((message) => {
      alert(message);
    });
  };
  document.body.appendChild(script);
</#noparse>
</script>
