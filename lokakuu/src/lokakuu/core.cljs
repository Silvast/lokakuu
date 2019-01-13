(ns lokakuu.core
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [reagent.core :as r]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [lokakuu.components.header :refer [header]]
            [lokakuu.components.blog :refer [blog]]
            [lokakuu.components.blogitem :refer [blogitem]]))

(def app-state (r/atom {}))

(defn about []
  [:div.container
   [header]
   [:div.container.pt-4
    [:h1 "About"]
    [:p "Who am I?"]]]
  [:a {:href "#/"} "home page"])

(defn blogpage []
  [:div.container
   [header]
   [:div.container.pt-4
    [:h1 "Blog posts"]
    [blog]]])

(defn blogpost [slug]
  [blogitem slug])

; (defn app
;   []
;   [:div.container
;    [header]
;    [:div.container.pt-4
;     [:h1 "etusivu"]
;     [blog]]])

(defn home []
  [:div.container
   [header (:active home)]
   [:div.container.pt-4
    [:h1 "etusivu"]
    [:p "Tämäpä on etusivu"]]
   [:a {:href "#/blog"} "blog page"]])

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
            (swap! app-state assoc :page :home))

  (defroute "/about" []
            (swap! app-state assoc :page :about))

  (defroute "/blog" []
            (swap! app-state assoc :page :blogpage))

  (defroute  "/blog/:slug" [slug]
             (swap! app-state assoc :page :blogpost)
             (swap! app-state assoc :params {:slug slug}))

  (hook-browser-navigation!))

(defmulti current-page #(@app-state :page))
(defmethod current-page :home []
  [home])
(defmethod current-page :about []
  [about])
(defmethod current-page :blogpage []
  [blogpage])
(defmethod current-page :blogpost []
  [blogpost (:slug (@app-state :params))])
(defmethod current-page :default []
  [:div])

(defn ^:export main
  []
  (app-routes)
  (r/render
    [current-page]
    (.getElementById js/document "app")))
