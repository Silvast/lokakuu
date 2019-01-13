(ns lokakuu.components.header
  (:require ["@material-ui/core/" :as material]))

(defn header []
  [:header.pt-3.pb-3
   ; [:> material/Button {:color "primary"} "foo"]
   [:div
    [:nav.nav.nav-fill.nav-pills.flex-column.flex-sm-row
     [:a.nav-item.nav-link.active {:href "#/"} "Home"]
     [:a.nav-item.nav-link {:href "#/blog"} "Blogi"]
     [:a.nav-item.nav-link {:href "#/about"} "About"]]]])
