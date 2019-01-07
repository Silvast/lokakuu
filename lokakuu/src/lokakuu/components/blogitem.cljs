(ns lokakuu.components.blogitem
  (:require [lokakuu.state :as state]
            [lokakuu.components.header :refer [header]]
            [cljs-http.client :as http]
            [lokakuu.http-util :as http-util]
            [cljs.core.async :as a :refer [<!]]
            [reagent.core :as r])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce blogitemdata (r/atom []))
(defonce old-slug (atom ""))

(defn blogitem [slug]
  (if (not= @old-slug slug)
    (do
      (go (let [response (<! (http-util/get-one-post slug))]
          (reset! blogitemdata (:body response))))
          (reset! old-slug slug)))
    [:div.container
      [header]
      [:h2 (:rendered (:title (first @blogitemdata)))]
      [:p {:dangerouslySetInnerHTML {:__html (:rendered (:excerpt (first @blogitemdata)))}}]])
