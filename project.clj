(defproject emotions-config "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clojurefx "0.0.12"]
                 [clj-time "0.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojars.ato/clojure-jsr223 "1.5.1"]
                 [reagi "0.6.3"]
                 [emotions "0.2.2"]
                 [expectations "1.4.56"]]
  :profile {:dev {:dependencies [[expectations "1.4.56"]]}}
  :plugins [[lein-autoexpect "1.0"]]
  :main emotions-config.core)
