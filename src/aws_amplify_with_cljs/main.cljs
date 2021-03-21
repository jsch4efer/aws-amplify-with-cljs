(ns aws-amplify-with-cljs.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            ["aws-amplify" :refer [Amplify] :as amplify]
            ["aws-amplify-react" :refer [SignOut withAuthenticator]]
            ["/aws-exports" :as awsconfig]))

(def functional-compiler (r/create-compiler {:function-components true}))
(r/set-default-compiler! functional-compiler)

(def config (.configure Amplify (.-default awsconfig)))

(defn pull-file! [name]
  (.. amplify/Storage
    (get name (clj->js {:download true}))
    (then #(.. % -Body text (then js/console.info))))
  )

(defn content []
  [:div
   "Hello, you are signed in"
    [:> SignOut]
   ])

(defn app []
  [:>
   (withAuthenticator
    (r/reactify-component content))]
  )

(defn main []
  (rdom/render [app] (js/document.getElementById "app")))

(defn load! []
  (main))

(defn reload! []
  (main))
