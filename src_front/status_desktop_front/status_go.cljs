(ns status-desktop-front.status-go)

;(def ipcRenderer (.-ipcRenderer (js/require "electron")))

(defn call-web3 [payload]
      (print "commented: call-web3")
  ;(.sendSync ipcRenderer "CallRPC" payload)
      )

(defn start-node [config]
      (print "commented: start-node")
  ;(.log js/console (.sendSync ipcRenderer "StartNode" config))
      )

(defn create-account [password]
  (.log js/console (str "CreateAccount " password))
      (print "commented: create-account")
  ;(let [res (.sendSync ipcRenderer "CreateAccount" password)]
  ;  (.log js/console (str "CreateAccount result " res))
  ;  res)
      )

(defn login [address password]
  (.log js/console (str "Login " address " " password))
  (print "commented: login")
  ;(let [res (.sendSync ipcRenderer "Login" address password)]
  ;  (.log js/console (str "Login result " res))
  ;  res)
      )

(defn recover-account [passphrase password]
      (print "commented: recover-account")
  ;(.sendSync ipcRenderer "RecoverAccount" passphrase password)
      )
