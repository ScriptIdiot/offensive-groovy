# Offensively Groovy

<p align="center">
  <img src="images/Why-I-Hate-Jenkins.png" alt="Why I Hate Jenkins">
</p>

## About

**Offensively Groovy** is a repository that documents how to use **Groovy** scripts for post-exploitation purposes. The project explores malicious capabilities of Groovy both for Windows and none-specific Operating Systems.

| Script                                                     | Description                                | Operating System |
| ---------------------------------------------------------- | ------------------------------------------ | ---------------- |
| [**`dll.groovy`**](windows/dll.groovy)                     | Load a DLL into Jenkins                    | Windows          |
| [**`ps.groovy`**](windows/ps.groovy)                       | Enumerate running processes with WinAPI    | Windows          |
| [**`sc.groovy`**](windows/sc.groovy)                       | Enumerate services with WinAPI             | Windows          |
| [**`shellcode.groovy`**](windows/shellcode.groovy)         | Execute shellcode commands with WinAPI     | Windows          |
| [**`cat.groovy`**](generic/cat.groovy)                     | Display the contents of a file             | Cross-platform   |
| [**`creds.groovy`**](generic/creds.groovy)                 | Extract Jenkins credentials                | Cross-platform   |
| [**`engines.groovy`**](generic/engines.groovy)             | List and manipulate Jenkins script engines | Cross-platform   |
| [**`env.groovy`**](generic/env.groovy)                     | Dump environment variables                 | Cross-platform   |
| [**`executors.groovy`**](generic/executors.groovy)         | List Jenkins executors                     | Cross-platform   |
| [**`exfil.groovy`**](generic/exfil.groovy)                 | Exfiltrate data over HTTP                  | Cross-platform   |
| [**`hostname.groovy`**](generic/hostname.groovy)           | Retrieve the system's hostname             | Cross-platform   |
| [**`ls.groovy`**](generic/ls.groovy)                       | List directory contents                    | Cross-platform   |
| [**`nodes.groovy`**](generic/nodes.groovy)                 | Enumerate Jenkins nodes                    | Cross-platform   |
| [**`oscmd.groovy`**](generic/oscmd.groovy)                 | Execute arbitrary OS commands              | Cross-platform   |
| [**`start_process.groovy`**](generic/start_process.groovy) | Start a background process                 | Cross-platform   |
| [**`sysinfo.groovy`**](generic/sysinfo.groovy)             | Retrieve system information                | Cross-platform   |
| [**`sysprops.groovy`**](generic/sysprops.groovy)           | Display system properties                  | Cross-platform   |
| [**`upload.groovy`**](generic/upload.groovy)               | Upload files to Jenkins                    | Cross-platform   |
| [**`ver.groovy`**](generic/ver.groovy)                     | Retrieve the OS version                    | Cross-platform   |
| [**`whoami.groovy`**](generic/whoami.groovy)               | Identify the current user                  | Cross-platform   |

## References
•	https://github.com/hoto/jenkins-credentials-decryptor

•	https://www.codurance.com/publications/2019/05/30/accessing-and-dumping-jenkins-credentials

•	https://www.exploit-db.com/docs/47374
