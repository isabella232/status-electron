(ns status-desktop-front.override.login
  (:require [re-frame.core :as re-frame]
            [status-im.ui.screens.accounts.login.events :as login.events]
            [status-desktop-front.status-go :as status-go]
            [status-im.utils.handlers :as handlers]
            [status-desktop-front.storage :as storage]))

;;;; FX

(re-frame/reg-fx ::login.events/stop-node (fn [])) ;(status/stop-node)))

(re-frame/reg-fx
  ::login.events/login
  (fn [[address password]]
    (re-frame/dispatch [:login-app (status-go/login address password (fn [data]
                                             (re-frame/dispatch [:login-handler data address])))]) ))

(re-frame/reg-fx ::login.events/clear-web-data #()) ;(status/clear-web-data))

(re-frame/reg-fx
  ::login.events/change-account
  (fn [[address new-account?]]
    (storage/change-account address new-account?)
    (add-watch (:chats @storage/account) :watcher (fn [key atom old-state new-state]
                                                    (remove-watch (:chats @storage/account) :watcher)
                                                    (re-frame/dispatch [:change-account-handler nil address new-account?])))

    ))


;;; Handlers

(handlers/register-handler-fx
  :login-account
  (fn [{{:keys [network status-node-started?] :as db} :db}
       [_ address password account-creation?]]
    (let [db' (-> db
                  (assoc :accounts/account-creation? account-creation?)
                  (assoc-in [:accounts/login :processing] true))]
      (login.events/wrap-with-login-account-fx db' address password))))