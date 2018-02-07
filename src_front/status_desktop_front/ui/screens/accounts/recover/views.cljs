(ns status-desktop-front.ui.screens.accounts.recover.views
  (:require-macros [status-im.utils.views :refer [defview letsubs]])
  (:require
    [status-im.ui.screens.accounts.login.styles :as st]
    [status-im.ui.screens.accounts.styles :as ast]
    [status-desktop-front.react-native-web :as react]
    [status-desktop-front.ui.components.views :as components]
    [re-frame.core :as re-frame]
    [status-desktop-front.ui.screens.accounts.views :as accounts.views]
    [clojure.string :as string]))

(defview recover []
  (letsubs [{:keys [passphrase password]} [:get :accounts/recover]
            profile-name [:get-in [:accounts/create :name]]]
    [react/view {:style (merge ast/accounts-container {:align-items :center :justify-content :center})}
     [components/back-button #(re-frame/dispatch [:navigate-back])]
     [react/view {:style {:align-items :center}}
      [react/view {:style {:margin-vertical 22}}
       [react/text {:style {:color :white :font-size 18}}
        "Recover account"]]
      [react/view {:style {:height 52 :width 290 :background-color "#FFFFFF4D"
                           :border-radius 8 :margin-top 22 :justify-content :center}}
       [react/text-input {:value       (or profile-name "")
                          :auto-focus  true
                          :style {:padding-horizontal 17 :color :white}
                          :placeholder "Name"
                          :on-change   (fn [e]
                                         (let [native-event (.-nativeEvent e)
                                               text (.-text native-event)]
                                           (re-frame/dispatch [:set-in [:accounts/create :name] text])))}]]
      [react/view {:style {:height 107 :width 290 :background-color "#FFFFFF4D"
                           :border-radius 8 :padding-vertical 17 :margin-top 8}}
       [react/text-input {:value       (or passphrase "")
                          :placeholder "Passphrase"
                          :multiline true
                          :style {:padding-horizontal 17 :color :white}
                          :on-change   (fn [e]
                                         (let [native-event (.-nativeEvent e)
                                               text (.-text native-event)]
                                           (re-frame/dispatch [:set-in [:accounts/recover :passphrase] text])))}]]
      [react/view {:style {:height 52 :width 290 :background-color "#FFFFFF4D" :margin-top 8
                           :border-radius 8 :justify-content :center}}
       [react/text-input {:value       (or password "")
                          :placeholder "Password"
                          :style {:padding-horizontal 17 :color :white}
                          :on-key-press (fn [e]
                                          (let [native-event (.-nativeEvent e)
                                                key (.-key native-event)]
                                            (when (= key "Enter")
                                              (re-frame/dispatch [:recover-account passphrase password])
                                              (re-frame/dispatch [:navigate-to-clean :accounts]))))
                          :secure-text-entry true
                          :on-change   (fn [e]
                                         (let [native-event (.-nativeEvent e)
                                               text (.-text native-event)]
                                           (re-frame/dispatch [:set-in [:accounts/recover :password] text])))}]]
      [react/view {:style {:margin-top 16}}
       [components/button
        "Recover"
        (> (count password) 6)
        #(do
           (re-frame/dispatch [:recover-account passphrase password])
           (re-frame/dispatch [:navigate-to-clean :accounts]))]]]]))
