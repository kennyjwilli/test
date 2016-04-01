(set-env!
  :resource-paths #{"src"}
  :wagons '[[s3-wagon-private "1.2.0"]]
  :repositories [["clojars" "http://clojars.org/repo/"]
                 ["maven-central" "http://repo1.maven.org/maven2/"]
                 ["provisdom" {:url        "s3p://provisdom-artifacts/releases/"
                               :username   (System/getenv "AWS_ACCESS_KEY")
                               :passphrase (System/getenv "AWS_SECRET_KEY")}]])

(def +version+ "0.1.0")

(task-options!
  pom {:project     'test-ci
       :version     +version+
       :description "A test"
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask release2
         "Developer workflow for web-component UX."
         [u access-key VALUE str "Username"
          p secret-key VALUE str "Password"]
         (comp
           (pom)
           (jar)
           (push :repo-map {:url        "s3p://provisdom-artifacts/releases/"
                            :username   access-key
                            :passphrase secret-key})))