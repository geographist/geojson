(defproject com.farmlogs/geojson "1.1.0"
  :description "Clojure utilities for using GeoJSON."
  :url "https://github.com/FarmLogs/geojson"
  :plugins [[s3-wagon-private "1.2.0"]]
  :repositories {"releases" {:url "s3p://fl-maven-repo/mvn"
                             :username :env/AMAZON_KEY
                             :passphrase :env/AMAZON_SECRET}}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/test.check "0.7.0"]])
