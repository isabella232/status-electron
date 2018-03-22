(ns ^:figwheel-no-load env.desktop.main
  (:require [reagent.core :as r]
              [status-desktop-front.core :as core]
              [figwheel.client :as fw]
              [env.config :as conf]
              [re-frisk-remote.core :refer [enable-re-frisk-remote! pre-event-callback]]))
  
    (enable-console-print!)


    (assert (exists? core/init) "Fatal Error - Your core.cljs file doesn't define an 'init' function!!! - Perhaps there was a compilation failure?")
    (assert (exists? core/app-root) "Fatal Error - Your core.cljs file doesn't define an 'app-root' function!!! - Perhaps there was a compilation failure?")
    
    (def cnt (r/atom 0))
    (defn reloader [] @cnt [core/app-root])
    
    ;; Do not delete, root-el is used by the figwheel-bridge.js
    (def root-el (r/as-element [reloader]))
    
    (fw/start {
                :websocket-url    (:desktop conf/figwheel-urls)
                :heads-up-display false
                :jsload-callback  #(swap! cnt inc)})

(enable-re-frisk-remote!)
(core/init)
