(ns lokakuu.components.blog
  (:require [lokakuu.state :as state]
            [cljs-http.client :as http]
            [lokakuu.http-util :as http-util]
            [cljs.core.async :as a :refer [<!]]
            [reagent.core :as r])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce blogdata (r/atom []))

(defonce request
  (go (let [response (<! (http-util/get-blog-posts))]
        (reset! blogdata (:body response)))))

(defn blog []
  (let [data @blogdata]
    [:div
     (map (fn [blogitem]
            [:div {:key (:id blogitem)}
             [:h2 [:a {:href (str "#/blog/" (:slug blogitem))} (:rendered (:title blogitem))]]
             [:p {:dangerouslySetInnerHTML {:__html (:rendered (:excerpt blogitem))}}]
             [:a {:href "#"}]]) data)]))
