KeyDumper
=========

This is a proof-of-concept plugin for
[CanaryMod](https://github.com/FallenMoonNetwork/CanaryMod) that dumps a
server's public and private keys for debugging.

Commands
--------

This plugin has only one command: `/dumpkeys`. Upon invoking this command, the
plugin will dump the public and private key with X509 and PKCS8 encoding,
respectively. The keys can be found in DER format in the server root as
`publicKey.der` and `privateKey.der`.

Requirements
------------

This plugin requires the latest version of CanaryMod. Builds for older versions
of CanaryMod can be built from source, and are tagged with `[plugin
version]~[minecraft version]`.

Compiling
---------

To compile, you need to put `CanaryMod.jar` and `minecraft_servero.jar` from CanaryMod
(instructions on how to obtain `minecraft_servero.jar` can be found in [their
README](https://github.com/FallenMoonNetwork/CanaryMod/blob/master/README.md))
in the `lib/` folder. The plugin can then be built by running `ant jar`.
